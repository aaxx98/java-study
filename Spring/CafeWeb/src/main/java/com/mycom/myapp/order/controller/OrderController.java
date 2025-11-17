package com.mycom.myapp.order.controller;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.order.dto.OrderDto;
import com.mycom.myapp.order.dto.OrderListDto;
import com.mycom.myapp.order.service.OrderService;
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
  public ResponseEntity<?> getOrderList(@ModelAttribute PageRequestDto requestDto) {
    if (requestDto.getPage() < 1 || requestDto.getPageSize() < 1) {
      return ResponseEntity
          .badRequest()
          .body("page와 pageSize는 1 이상의 값이어야 합니다.");
    }
    OrderListDto listDto = orderService.getOrderList(requestDto);

    return ResponseEntity.ok(listDto);
  }

  @GetMapping("/{orderId}")
  public OrderDto getOrderById(@PathVariable int orderId) {
    return orderService.getOrderById(orderId);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
    boolean deleted = orderService.deleteById(id);
    if (!deleted) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<?> saveOrder(@RequestBody OrderDto order) {
    boolean result = orderService.createOrder(order);
    if (result) {
      return ResponseEntity.ok().build();
    }
    throw new IllegalArgumentException("주문 정보가 정확하지 않습니다.");
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
