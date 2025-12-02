package com.mycom.myapp.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItem {

  private int id;
  private int orderId;
  private int productId;
  private int quantity;
  private int price;
}
