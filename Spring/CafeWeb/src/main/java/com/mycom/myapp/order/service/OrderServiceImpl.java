package com.mycom.myapp.order.service;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.common.entity.Order;
import com.mycom.myapp.common.entity.OrderItem;
import com.mycom.myapp.common.exception.BadRequestException;
import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.order.dao.OrderDao;
import com.mycom.myapp.order.dto.CreateOrderRequest;
import com.mycom.myapp.order.dto.OrderListResponse;
import com.mycom.myapp.order.dto.OrderResponse;
import com.mycom.myapp.order.dto.UpdateOrderStateRequest;
import com.mycom.myapp.orderItem.dao.OrderItemDao;
import com.mycom.myapp.orderItem.dto.OrderItemRequest;
import com.mycom.myapp.product.dao.ProductDao;
import com.mycom.myapp.product.dto.ProductResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class OrderServiceImpl implements OrderService {

  private final OrderDao orderDao;
  private final OrderItemDao orderItemDao;
  private final ProductDao productDao;

  public OrderServiceImpl(OrderDao orderDao, OrderItemDao orderItemDao, ProductDao productDao) {
    this.orderDao = orderDao;
    this.orderItemDao = orderItemDao;
    this.productDao = productDao;
  }

  public OrderListResponse getOrderList(PageRequest request) {
    if (request.getPage() < 1 || request.getPageSize() < 1) {
      throw new IllegalArgumentException("page와 pageSize는 1 이상의 값이어야 합니다.");
    }

    List<OrderResponse> list = orderDao.findAllOrders(request);
    int totalCount = orderDao.countAll();
    int totalPages = (int) Math.ceil((double) totalCount / request.getPageSize());

    OrderListResponse response = new OrderListResponse();
    response.setList(list);
    response.setTotalCount(totalCount);
    response.setTotalPages(totalPages);
    response.setCurrentPage(request.getPage());
    return response;
  }

  public OrderResponse getOrderById(int orderId) {
    OrderResponse order = orderDao.findOrderById(orderId);
    if (order == null) {
      throw new NotFoundException("주문을 찾을 수 없습니다.");
    }
    return order;
  }

  @Transactional
  public void deleteById(int id) {
    OrderResponse order = orderDao.findOrderById(id);
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
  public int createOrder(CreateOrderRequest order) {
    Order saveOrder = Order.builder().
        status("ORDER").orderDate(LocalDate.now()).build();
    int inserted = orderDao.registerOrder(saveOrder);
    if (inserted <= 0) {
      throw new IllegalStateException("주문 생성에 실패했습니다.");
    }
    int orderId = saveOrder.getId();

    for (OrderItemRequest item : order.getOrderItems()) {
      ProductResponse product = productDao.findById(item.getProductId());
      if (product == null) {
        throw new BadRequestException(
            "주문 불가능한 상품이 주문 목록에 포함되어있습니다. [productId: " + item.getProductId() + "]");
      }

      OrderItem saveItem = OrderItem.builder().
          orderId(orderId).
          productId(item.getProductId()).
          price(product.getPrice() * item.getQuantity()).
          quantity(item.getQuantity()).
          build();
      int insertedItem = orderItemDao.registerOrderItem(saveItem);
      if (insertedItem <= 0) {
        throw new IllegalStateException("주문 생성에 실패했습니다.");
      }
    }
    return saveOrder.getId();
  }

  public void updateOrderStatus(UpdateOrderStateRequest request) {
    OrderResponse order = orderDao.findOrderById(request.getId());
    if (order == null) {
      throw new NotFoundException("주문을 찾을 수 없습니다.");
    }

    Order updateOrder = Order.builder().
        id(request.getId()).
        status(request.getStatus()).
        build();
    int updated = orderDao.updateOrderStatus(updateOrder);
    if (updated <= 0) {
      throw new IllegalStateException("주문 상태 변경에 실패했습니다.");
    }
  }
}
