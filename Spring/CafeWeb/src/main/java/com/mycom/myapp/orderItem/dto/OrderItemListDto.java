package com.mycom.myapp.orderItem.dto;

import com.mycom.myapp.order.dto.OrderDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemListDto {

  private OrderDto order;
  private List<OrderItemDto> list;
}
