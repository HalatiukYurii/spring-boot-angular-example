package com.sda.springbootdemo.exercises.service;

import static org.springframework.util.CollectionUtils.isEmpty;

import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.model.Product;
import com.sda.springbootdemo.exercises.model.Receipt;
import com.sda.springbootdemo.exercises.repository.ReceiptRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository receiptRepository;

    public Double summary(UUID id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Receipt with %s not found", id)));

        return receipt.getProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public Double summary(Collection<UUID> ids) {
        List<Receipt> receipts;
        if (isEmpty(ids)) {
            receipts = receiptRepository.findAll();
        } else {
            receipts = receiptRepository.findAllById(ids);
        }

        return receipts
                .stream()
                .flatMap(receipt -> receipt.getProducts().stream())
                .mapToDouble(Product::getPrice)
                .sum();
    }
}
