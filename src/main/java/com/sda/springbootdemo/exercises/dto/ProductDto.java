package com.sda.springbootdemo.exercises.dto;

import com.sda.springbootdemo.exercises.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseDto {

    protected String name;
    protected Double price;

    public ProductDto(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }

    public ProductDto(UUID id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
