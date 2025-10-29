package myProj.service;

import java.time.LocalDate;
import java.util.List;
import myProj.dto.OrderItemDetailDTO;
import myProj.dto.OrderSummaryDTO;
import myProj.dto.ProductDTO;

public interface OrderService {

  List<OrderSummaryDTO> getOrdersByDate(LocalDate date);

  List<ProductDTO> getAllProducts();

  void createOrder(List<OrderItemDetailDTO> orderItems);

  List<OrderItemDetailDTO> getOrderItems(Integer orderId);


  void updateOrderStatus(Integer orderId, String status);
}
