package com.sda.springbootdemo.exercises.repository;

import com.sda.springbootdemo.exercises.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    Page<Product> findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
        String name,
        Double minPrice,
        Double maxPrice,
        Pageable pageable);
}
