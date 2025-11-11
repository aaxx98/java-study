package com.mycom.myapp.order.dao;

import com.mycom.myapp.order.dto.OrderDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

  List<OrderDto> findAllOrders();
  OrderDto findOrderById(@Param("orderId") int orderId);
}