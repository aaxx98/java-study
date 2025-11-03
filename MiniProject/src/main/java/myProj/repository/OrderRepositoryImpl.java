package myProj.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import myProj.domain.Orders;

public class OrderRepositoryImpl implements OrderRepository {

  private final EntityManager em;

  public OrderRepositoryImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public void save(Orders order) {
    if (order.getId() == 0) {
      em.persist(order);
    } else {
      em.merge(order);
    }
  }

  @Override
  public Optional<Orders> findById(Integer id) {
    return Optional.ofNullable(em.find(Orders.class, id));
  }

  @Override
  public List<Orders> findByOrderDate(LocalDate date) {
    TypedQuery<Orders> query = em.createQuery(
        "SELECT o FROM Orders o WHERE o.orderDate = :date ORDER BY o.id DESC",
        Orders.class
    );
    query.setParameter("date", date);
    return query.getResultList();
  }

  @Override
  public void updateStatus(Integer orderId, String status) {
    Orders order = em.find(Orders.class, orderId);
    if (order != null) {
      order.setStatus(status);
    }
  }
}
