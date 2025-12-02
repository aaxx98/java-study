package com.mycom.myapp.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {

  private int id;
  private int productId;
  private String quantity;
}
