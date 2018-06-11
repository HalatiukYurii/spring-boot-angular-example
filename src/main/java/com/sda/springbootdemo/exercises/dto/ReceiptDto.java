package com.sda.springbootdemo.exercises.dto;

import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.model.Receipt;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDto extends BaseDto {

    private String buyersName;
    private LocalDateTime date;
    private List<ProductDto> products;

    public ReceiptDto(Receipt receipt) {
        this(receipt.getId(), receipt.getBuyer(), receipt.getDate(), receipt.getProducts());
    }

    public ReceiptDto(UUID id, String buyersName, LocalDateTime date, List<Product> products) {
        this.id = id;
        this.buyersName = buyersName;
        this.date = date;
        this.products = products
            .stream()
            .map(ProductDto::new)
            .collect(Collectors.toList());
    }
}
