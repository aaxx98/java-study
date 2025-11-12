package com.mycom.myapp.stock.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockListDto {

  private List<StockDto> list;
  private int totalCount;
  private int totalPages;
  private int currentPage;
}
