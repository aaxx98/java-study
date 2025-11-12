package com.mycom.myapp.order.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderListDto {

  private List<OrderDto> list;
  private int totalCount;
  private int totalPages;
  private int currentPage;
}
