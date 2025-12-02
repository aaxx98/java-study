package com.mycom.myapp.order.dao;

import com.mycom.myapp.common.dto.PageRequest;
import com.mycom.myapp.common.entity.Order;
import com.mycom.myapp.order.dto.OrderResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

  List<OrderResponse> findAllOrders(PageRequest req);

  int countAll();

  OrderResponse findOrderById(@Param("orderId") int orderId);

  int deleteById(@Param("id") int id);

  int registerOrder(Order order);

  int updateOrderStatus(Order order);
}