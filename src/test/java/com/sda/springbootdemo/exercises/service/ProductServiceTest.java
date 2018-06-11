package com.sda.springbootdemo.exercises.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.exception.ValidationException;
import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.repository.ProductRepository;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private Product product;
    private Pageable pageable;

    @Before
    public void setUp() {
        product = new Product("name", 1.1);
        product.setId(UUID.randomUUID());

        pageable = new PageRequest(0, 10);

        when(productRepository.findById(product.getId()))
            .thenReturn(Optional.of(product));
    }

    @Test
    public void contextLoads() {
        assertThat(productService).isNotNull();
    }

    @Test
    public void shouldReturnProductById() {
        Product result = productService.get(product.getId());
        assertEquals(product, result);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExecption() {
        when(productRepository.findById(product.getId()))
            .thenReturn(Optional.empty());

        productService.get(product.getId());
    }

    @Test
    public void shouldThrowNotFoundExecption2() {
      expectedEx.expect(NotFoundException.class);
      expectedEx.expectMessage(String.format("Product with id %s not found", product.getId()));

      when(productRepository.findById(product.getId()))
          .thenReturn(Optional.empty());

      productService.get(product.getId());
    }

    @Test
    public void shouldSearchByAllParameters() {
        Page<Product> page = new PageImpl(Collections.singletonList(product), pageable, 1);
        when(productRepository
            .findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
                any(String.class), any(Double.class), any(Double.class), any(Pageable.class)
            ))
            .thenReturn(page);

        Page<Product> result = productService.search("name", 10d, 100d, pageable);

        assertEquals(page, result);
        verify(productRepository, times(1))
            .findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
                "name", 10d, 100d, pageable);
    }

    @Test
    public void shouldSetMaxPriceParameterToMaxDouble() {
      Page<Product> page = new PageImpl(Collections.singletonList(product), pageable, 1);
      when(productRepository
          .findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
              any(String.class), any(Double.class), eq(Double.MAX_VALUE), any(Pageable.class)
          ))
          .thenReturn(page);

      Page<Product> result = productService.search("name", 10d, null, pageable);

      assertEquals(page, result);
      verify(productRepository, times(1))
          .findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
              "name", 10d, Double.MAX_VALUE, pageable);
    }
}
