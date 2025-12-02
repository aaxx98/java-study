package com.mycom.myapp.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

  private int id;
  private String name;
  private String category;
  private int price;
  private boolean stockManage;
}
