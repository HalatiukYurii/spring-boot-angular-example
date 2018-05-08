package com.sda.springbootdemo.exercises.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.sda.springbootdemo.exercises.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryController {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    public void testExample() throws Exception {
        this.entityManager.persist(new Product("some product", 12.1));
        Product product = this.repository.getOne(1L);
        assertThat(product.getName()).isEqualTo("some product");
        assertThat(product.getPrice()).isEqualTo(12.1);
    }
}
