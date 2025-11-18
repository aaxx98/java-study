package com.mycom.myapp.orderItem.service;

import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.order.dao.OrderDao;
import com.mycom.myapp.order.dto.OrderDto;
import com.mycom.myapp.orderItem.dao.OrderItemDao;
import com.mycom.myapp.orderItem.dto.OrderItemDto;
import com.mycom.myapp.orderItem.dto.OrderItemListDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

  private final OrderItemDao orderItemDao;
  private final OrderDao orderDao;

  public OrderItemService(OrderItemDao orderItemDao, OrderDao orderDao) {
    this.orderItemDao = orderItemDao;
    this.orderDao = orderDao;
  }

  public OrderItemListDto getOrderItemListByOrderId(int orderId) {
    List<OrderItemDto> list = orderItemDao.findOrderItemsByOrderId(orderId);
    OrderDto orderInfo = orderDao.findOrderById(orderId);
    if (orderInfo == null) {
      throw new NotFoundException("주문 정보를 찾을 수 없습니다.");
    }

    OrderItemListDto response = new OrderItemListDto();
    response.setList(list);
    response.setOrder(orderInfo);
    return response;
  }
}
