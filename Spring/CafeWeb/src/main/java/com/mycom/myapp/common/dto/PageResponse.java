package com.mycom.myapp.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse {

  private int totalCount;
  private int totalPages;
  private int currentPage;
}
