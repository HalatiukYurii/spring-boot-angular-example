package com.sda.springbootdemo.exercises.repository.custom;

import com.sda.springbootdemo.exercises.model.Receipt;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReceiptRepositoryCustom {
    Page<Receipt> search(
        String buyer,
        LocalDateTime startDate, LocalDateTime endDate,
        String productName,
        Pageable pageable);
}
