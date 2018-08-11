package com.sda.springbootdemo.exercises.controller;

import com.sda.springbootdemo.exercises.model.CurrencyRateInfo;
import com.sda.springbootdemo.exercises.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

  @Autowired
  private CurrencyService currencyService;

  @GetMapping("/{code}")
  @ResponseStatus(HttpStatus.OK)
  public CurrencyRateInfo getCurrecnyInfo(@PathVariable String code) {
    return currencyService.getCurrencyRateInfo(code);
  }
}
