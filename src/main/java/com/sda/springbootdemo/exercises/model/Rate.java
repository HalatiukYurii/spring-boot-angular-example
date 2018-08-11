package com.sda.springbootdemo.exercises.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class Rate {

  private String no;
  private LocalDate effectiveDate;
  private Double mid;
}
