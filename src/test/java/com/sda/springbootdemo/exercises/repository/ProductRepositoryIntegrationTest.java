package com.sda.springbootdemo.exercises.repository;


import com.sda.springbootdemo.exercises.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    private Product product;

    @Before
    public void setUp() {
        product = repository.save(new Product("asd", 10d));
    }

    @Test
    public void shouldGetProductById1() {
        System.out.println(repository.findAll().size());
        System.out.println(product.getId());
        Product foundProduct = repository.findById(product.getId()).get();
        assertThat(foundProduct.getName()).isEqualTo(product.getName());
        assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice());
    }
}
