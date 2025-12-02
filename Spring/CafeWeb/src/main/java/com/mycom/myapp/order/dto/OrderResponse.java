package com.mycom.myapp.order.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

  private int id;
  private LocalDate orderDate;
  private String status;

  private int totalQuantity;
  private int totalPrice;
}
