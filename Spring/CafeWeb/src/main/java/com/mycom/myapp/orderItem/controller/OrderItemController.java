package com.mycom.myapp.orderItem.controller;

import com.mycom.myapp.orderItem.dto.OrderItemListResponse;
import com.mycom.myapp.orderItem.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/items")
public class OrderItemController {

  private final OrderItemService orderItemService;

  public OrderItemController(OrderItemService orderItemService) {
    this.orderItemService = orderItemService;
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderItemListResponse> getOrderList(@PathVariable int orderId) {
    OrderItemListResponse listDto = orderItemService.getOrderItemListByOrderId(orderId);
    return ResponseEntity.ok(listDto);
  }
}
