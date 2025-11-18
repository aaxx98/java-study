package com.mycom.myapp.order.controller;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.order.dto.OrderDto;
import com.mycom.myapp.order.dto.OrderListDto;
import com.mycom.myapp.order.service.OrderService;
import java.net.URI;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<OrderListDto> getOrderList(@ModelAttribute PageRequestDto requestDto) {
    OrderListDto listDto = orderService.getOrderList(requestDto);
    return ResponseEntity.ok(listDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable int orderId) {
    OrderDto order = orderService.getOrderById(orderId);
    return ResponseEntity.ok(order);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
    orderService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> saveOrder(@RequestBody OrderDto order) {
    int createdId = orderService.createOrder(order);
    URI location = URI.create("/api/items/" + createdId);
    return ResponseEntity.created(location).build();
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateOrderStatus(
      @PathVariable int id,
      @RequestBody Map<String, String> request
  ) {
    String status = request.get("status");
    orderService.updateOrderStatus(id, status);
    return ResponseEntity.ok().build();
  }
}
