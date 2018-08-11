package com.sda.springbootdemo.exercises.model;

import java.util.List;
import lombok.Data;

@Data
public class CurrencyRateInfo {

  private String table;
  private String currency;
  private String code;
  private List<Rate> rates;
}
