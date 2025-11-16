package com.mycom.myapp.orderItem.dao;

import com.mycom.myapp.orderItem.dto.OrderItemDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemDao {

  List<OrderItemDto> findOrderItemsByOrderId(@Param("orderId") int orderId);

  void deleteByOrderId(@Param("orderId") int orderId);

  void registerOrderItem(OrderItemDto orderItem);
}
