package com.mycom.myapp.order.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.order.dao.OrderDao;
import com.mycom.myapp.order.dto.OrderDto;
import com.mycom.myapp.order.dto.OrderListDto;
import com.mycom.myapp.orderItem.dao.OrderItemDao;
import com.mycom.myapp.orderItem.dto.OrderItemDto;
import com.mycom.myapp.product.dao.ProductDao;
import com.mycom.myapp.product.dto.ProductDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

  private final OrderDao orderDao;
  private final OrderItemDao orderItemDao;
  private final ProductDao productDao;

  public OrderService(OrderDao orderDao, OrderItemDao orderItemDao, ProductDao productDao) {
    this.orderDao = orderDao;
    this.orderItemDao = orderItemDao;
    this.productDao = productDao;
  }

  public OrderListDto getOrderList(PageRequestDto request) {
    List<OrderDto> list = orderDao.findAllOrders(request);
    int totalCount = orderDao.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    OrderListDto response = new OrderListDto();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;
  }

  public OrderDto getOrderById(int orderId) {
    return orderDao.findOrderById(orderId);
  }

  @Transactional
  public boolean deleteById(int id) {
    OrderDto order = orderDao.findOrderById(id);
    if (order != null) {
      orderItemDao.deleteByOrderId(id);
      orderDao.deleteById(id);
      return true;
    }
    return false;
  }

  @Transactional
  public boolean createOrder(OrderDto order) {
    OrderDto saveOrder = new OrderDto();
    saveOrder.setStatus("ORDER");
    saveOrder.setOrderDate(LocalDate.now());
    orderDao.registerOrder(saveOrder);
    int orderId = saveOrder.getId();

    for (OrderItemDto item : order.getOrderItems()) {
      ProductDto product = productDao.findById(item.getProductId());
      if (product == null) {
        return false;
      }
      item.setOrderId(orderId);
      item.setPrice(product.getPrice() * item.getQuantity());
      orderItemDao.registerOrderItem(item);
    }
    return true;
  }
}
