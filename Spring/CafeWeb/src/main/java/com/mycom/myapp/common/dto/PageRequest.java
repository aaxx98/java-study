package com.mycom.myapp.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {

  private int page = 1;
  private int pageSize = 30;
  private String keyword = "";

  public int getOffset() {
    return (page - 1) * pageSize;
  }
}
