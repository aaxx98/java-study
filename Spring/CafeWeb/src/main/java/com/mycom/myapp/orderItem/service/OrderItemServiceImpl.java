package com.mycom.myapp.orderItem.service;

import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.order.dao.OrderDao;
import com.mycom.myapp.order.dto.OrderResponse;
import com.mycom.myapp.orderItem.dao.OrderItemDao;
import com.mycom.myapp.orderItem.dto.OrderItemListResponse;
import com.mycom.myapp.orderItem.dto.OrderItemResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

  private final OrderItemDao orderItemDao;
  private final OrderDao orderDao;

  public OrderItemServiceImpl(OrderItemDao orderItemDao, OrderDao orderDao) {
    this.orderItemDao = orderItemDao;
    this.orderDao = orderDao;
  }

  public OrderItemListResponse getOrderItemListByOrderId(int orderId) {
    List<OrderItemResponse> list = orderItemDao.findOrderItemsByOrderId(orderId);
    OrderResponse orderInfo = orderDao.findOrderById(orderId);
    if (orderInfo == null) {
      throw new NotFoundException("주문 정보를 찾을 수 없습니다.");
    }

    OrderItemListResponse response = new OrderItemListResponse();
    response.setList(list);
    response.setOrder(orderInfo);
    return response;
  }
}
