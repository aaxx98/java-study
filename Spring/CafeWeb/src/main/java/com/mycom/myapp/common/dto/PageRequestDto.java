package com.mycom.myapp.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {

  private int page = 1;
  private int pageSize = 30;

  public int getOffset() {
    return (page - 1) * pageSize;
  }
}
