package com.mycom.myapp.order.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.order.dto.CreateOrderRequest;
import com.mycom.myapp.order.dto.OrderListResponse;
import com.mycom.myapp.order.dto.OrderResponse;
import com.mycom.myapp.order.dto.UpdateOrderStateRequest;

public interface OrderService {

  OrderListResponse getOrderList(PageRequest request);

  OrderResponse getOrderById(int orderId);

  void deleteById(int id);

  int createOrder(CreateOrderRequest order);

  void updateOrderStatus(UpdateOrderStateRequest request);
}