package com.mycom.myapp.order.dto;

import com.mycom.myapp.orderItem.dto.OrderItemRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {

  private List<OrderItemRequest> orderItems;
}
