package com.mycom.myapp.order.dao;

import com.mycom.myapp.common.dto.PageRequestDto;
import com.mycom.myapp.order.dto.OrderDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

  List<OrderDto> findAllOrders(PageRequestDto req);

  int countAll();

  OrderDto findOrderById(@Param("orderId") int orderId);

  int deleteById(@Param("id") int id);

  int registerOrder(OrderDto order);

  int updateOrderStatus(OrderDto dto);
}