package com.mycom.myapp.orderItem.service;

import com.mycom.myapp.orderItem.dto.OrderItemListResponse;

public interface OrderItemService {

  public OrderItemListResponse getOrderItemListByOrderId(int orderId);

}