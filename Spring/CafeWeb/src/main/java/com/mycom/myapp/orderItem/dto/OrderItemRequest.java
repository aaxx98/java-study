package com.mycom.myapp.orderItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

  private int productId;
  private int orderId;
  private int quantity;
}
