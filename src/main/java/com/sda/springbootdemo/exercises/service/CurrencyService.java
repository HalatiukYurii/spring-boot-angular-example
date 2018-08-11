package com.sda.springbootdemo.exercises.service;

import com.sda.springbootdemo.exercises.model.CurrencyRateInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyService {

  private RestOperations restTemplate = new RestTemplate();

  public CurrencyRateInfo getCurrencyRateInfo(String currencyName) {
    return restTemplate.getForEntity(
        "http://api.nbp.pl/api/exchangerates/rates/a/" + currencyName,
        CurrencyRateInfo.class)
      .getBody();
  }
}
