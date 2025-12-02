package com.mycom.myapp.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

  private int id;
  private String name;
  private String category;
  private int price;
  private boolean stockManage;
}
