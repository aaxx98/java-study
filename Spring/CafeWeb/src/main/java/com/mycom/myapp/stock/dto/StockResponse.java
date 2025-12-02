package com.mycom.myapp.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponse {

  private int id;
  private int productId;
  private String quantity;
}

