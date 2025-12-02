package com.mycom.myapp.order.controller;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.order.dto.CreateOrderRequest;
import com.mycom.myapp.order.dto.OrderListResponse;
import com.mycom.myapp.order.dto.OrderResponse;
import com.mycom.myapp.order.dto.UpdateOrderStateRequest;
import com.mycom.myapp.order.service.OrderService;
import java.net.URI;
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
  public ResponseEntity<OrderListResponse> getOrderList(@ModelAttribute PageRequest request) {
    OrderListResponse listDto = orderService.getOrderList(request);
    return ResponseEntity.ok(listDto);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrderById(@PathVariable int orderId) {
    OrderResponse order = orderService.getOrderById(orderId);
    return ResponseEntity.ok(order);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
    orderService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Void> saveOrder(@RequestBody CreateOrderRequest request) {
    int createdId = orderService.createOrder(request);
    URI location = URI.create("/api/items/" + createdId);
    return ResponseEntity.created(location).build();
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> updateOrderStatus(
      @PathVariable int id,
      @RequestBody UpdateOrderStateRequest request
  ) {
    request.setId(id);
    orderService.updateOrderStatus(request);
    return ResponseEntity.ok().build();
  }
}
