package com.mycom.myapp.order.dto;

import com.mycom.myapp.orderItem.dto.OrderItemDto;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

  private int id;
  private LocalDate orderDate;
  private String status;

  private int totalQuantity;
  private int totalPrice;
  private List<OrderItemDto> orderItems;
}
