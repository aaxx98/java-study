package com.mycom.myapp.common.entity;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

  private int id;
  private LocalDate orderDate;
  private String status;
}
