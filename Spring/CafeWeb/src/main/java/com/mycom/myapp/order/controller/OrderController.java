package com.mycom.myapp.order.controller;

import com.mycom.myapp.order.dto.OrderDto;
import com.mycom.myapp.order.service.OrderService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public List<OrderDto> getOrderList() {
    return orderService.getOrderList();
  }

  @GetMapping("/{orderId}")
  public OrderDto getOrderById(@PathVariable int orderId) {
    return orderService.getOrderById(orderId);
  }
}
