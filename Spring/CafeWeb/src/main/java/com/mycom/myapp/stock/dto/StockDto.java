package com.mycom.myapp.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDto {

  private int id;
  private int productId;
  private String productName;
  private String quantity;
}
