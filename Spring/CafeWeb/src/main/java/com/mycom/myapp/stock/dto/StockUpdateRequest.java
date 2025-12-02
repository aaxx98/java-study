package com.mycom.myapp.stock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockUpdateRequest {

  private int productId;
  private int add;
}
