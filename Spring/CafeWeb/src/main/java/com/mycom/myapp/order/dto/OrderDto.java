package com.mycom.myapp.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

  private int id;
  private int totalQuantity;
  private int totalPrice;
  private String status;
}
