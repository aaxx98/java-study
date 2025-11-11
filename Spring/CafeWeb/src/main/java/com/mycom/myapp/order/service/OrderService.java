package com.mycom.myapp.order.service;

import com.mycom.myapp.order.dao.OrderDao;
import com.mycom.myapp.order.dto.OrderDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderDao orderDao;

  public OrderService(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  public List<OrderDto> getOrderList() {
    return orderDao.findAllOrders();
  }

  public OrderDto getOrderById(int orderId) {
    return orderDao.findOrderById(orderId);
  }
}
