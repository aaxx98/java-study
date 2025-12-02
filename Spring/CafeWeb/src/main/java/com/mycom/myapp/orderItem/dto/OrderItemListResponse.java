package com.mycom.myapp.orderItem.dto;

import com.mycom.myapp.order.dto.OrderResponse;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemListResponse {

  private OrderResponse order;
  private List<OrderItemResponse> list;
}
