package com.mycom.myapp.order.service;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.common.exception.BadRequestException;
import com.mycom.myapp.common.exception.NotFoundException;
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
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new IllegalArgumentException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

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
    OrderDto order = orderDao.findOrderById(orderId);
    if (order == null) {
      throw new NotFoundException("주문을 찾을 수 없습니다.");
    }
    return order;
  }

  @Transactional
  public void deleteById(int id) {
    OrderDto order = orderDao.findOrderById(id);
    if (order == null) {
      throw new NotFoundException("주문을 찾을 수 없습니다.");
    }
    orderItemDao.deleteByOrderId(id);
    int deleted = orderDao.deleteById(id);
    if (deleted <= 0) {
      throw new IllegalStateException("주문 삭제에 실패했습니다.");
    }
  }

  @Transactional
  public int createOrder(OrderDto order) {
    OrderDto saveOrder = new OrderDto();
    saveOrder.setStatus("ORDER");
    saveOrder.setOrderDate(LocalDate.now());
    int inserted = orderDao.registerOrder(saveOrder);
    if (inserted <= 0) {
      throw new IllegalStateException("주문 생성에 실패했습니다.");
    }
    int orderId = saveOrder.getId();

    for (OrderItemDto item : order.getOrderItems()) {
      ProductDto product = productDao.findById(item.getProductId());
      if (product == null) {
        throw new BadRequestException(
            "주문 불가능한 상품이 주문 목록에 포함되어있습니다. [productId: " + item.getProductId() + "]");
      }
      item.setOrderId(orderId);
      item.setPrice(product.getPrice() * item.getQuantity());
      int insertedItem = orderItemDao.registerOrderItem(item);
      if (insertedItem <= 0) {
        throw new IllegalStateException("주문 생성에 실패했습니다.");
      }
    }
    return saveOrder.getId();
  }

  public void updateOrderStatus(int id, String status) {
    OrderDto order = orderDao.findOrderById(id);
    if (order == null) {
      throw new NotFoundException("주문을 찾을 수 없습니다.");
    }

    OrderDto dto = new OrderDto();
    dto.setId(id);
    dto.setStatus(status);
    int updated = orderDao.updateOrderStatus(dto);
    if (updated <= 0) {
      throw new IllegalStateException("주문 상태 변경에 실패했습니다.");
    }
  }
}
