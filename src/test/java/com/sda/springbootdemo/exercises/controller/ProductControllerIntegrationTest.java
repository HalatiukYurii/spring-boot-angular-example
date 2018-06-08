package com.sda.springbootdemo.exercises.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.repository.ProductRepository;
import com.sda.springbootdemo.exercises.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository repository;

    @Test
    public void shouldGetAllProducts() throws Exception {
        Long id = 1L;
        when(productService.get(eq(id)))
                .thenReturn(new Product("some product", 1.1));

        mockMvc.perform(get("/products/" + id))
            .andDo(print())
            .andExpect(status().isOk());

        verify(productService, times(1)).get(id);
    }

    @Test
    public void shouldReturn404WhenNoProductFound() throws Exception {
        Long id = 1L;
        when(productService.get(eq(id)))
            .thenThrow(new NotFoundException("no product found"));

        mockMvc.perform(get("/products/" + id))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(content().string("no product found"));

        verify(productService, times(1)).get(id);
    }
}
