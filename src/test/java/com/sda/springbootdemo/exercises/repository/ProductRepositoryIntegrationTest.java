package com.sda.springbootdemo.exercises.repository;


import static org.assertj.core.api.Assertions.assertThat;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    public void shouldGetProductById() throws Exception {
        this.entityManager.persist(new Product("some product", 12.1));
        Product product = repository.getOne(1L);
        assertThat(product.getName()).isEqualTo("some product");
        assertThat(product.getPrice()).isEqualTo(12.1);
    }
}
