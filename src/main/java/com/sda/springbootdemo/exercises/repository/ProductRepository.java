package com.sda.springbootdemo.exercises.repository;

import com.sda.springbootdemo.exercises.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
    Page<Product> findByNameIgnoreCaseContainingAndPriceGreaterThanEqualAndPriceLessThanEqual(
        String name,
        Double minPrice,
        Double maxPrice,
        Pageable pageable);
}
