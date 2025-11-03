package myProj.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import myProj.domain.Orders;

public interface OrderRepository {

  void save(Orders order);

  Optional<Orders> findById(Integer id);

  List<Orders> findByOrderDate(LocalDate date);

  void updateStatus(Integer orderId, String status);
}
