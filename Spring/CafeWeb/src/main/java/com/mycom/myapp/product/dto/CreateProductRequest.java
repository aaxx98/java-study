package com.mycom.myapp.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

  private String name;
  private String category;
  private int price;
  private boolean stockManage;
}
