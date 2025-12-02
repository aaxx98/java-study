package com.mycom.myapp.orderItem.dao;

import com.mycom.myapp.common.entity.OrderItem;
import com.mycom.myapp.orderItem.dto.OrderItemResponse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemDao {

  List<OrderItemResponse> findOrderItemsByOrderId(@Param("orderId") int orderId);

  void deleteByOrderId(@Param("orderId") int orderId);

  int registerOrderItem(OrderItem orderItem);
}
