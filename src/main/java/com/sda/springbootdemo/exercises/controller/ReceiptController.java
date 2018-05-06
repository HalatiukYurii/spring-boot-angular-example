package com.sda.springbootdemo.exercises.controller;

import com.sda.springbootdemo.exercises.exception.BindingResultException;
import com.sda.springbootdemo.exercises.exception.NotFoundException;
import com.sda.springbootdemo.exercises.model.Receipt;
import com.sda.springbootdemo.exercises.repository.ReceiptRepository;
import com.sda.springbootdemo.exercises.service.ReceiptService;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ReceiptRepository receiptRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receipt create(@RequestBody @Valid Receipt receipt, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        return receiptRepository.save(receipt);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Receipt> search(
            @RequestParam(value = "buyer", required = false) String buyer,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "productName", required = false) String productName,
            Pageable pageable) {
        return receiptRepository.search(
            buyer,
            null != startDate ? startDate.atStartOfDay() : null,
            null != endDate ? endDate.atTime(LocalTime.MAX) : null,
            productName,
            pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Receipt get(@PathVariable("id") Long id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No receipt find with %s id", id)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Receipt update(@PathVariable("id") Long id, @RequestBody Receipt receipt) {
        if (receiptRepository.existsById(id)) {
            throw new NotFoundException(String.format("No receipt find with %s id", id));
        }
        receipt.setId(id);
        return receiptRepository.save(receipt);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Long id) {
        Receipt receipt = receiptRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Receipt with id %s not found", id)));
        receiptRepository.delete(receipt);
    }
}
