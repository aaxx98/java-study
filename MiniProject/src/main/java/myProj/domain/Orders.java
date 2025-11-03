package myProj.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increase
  private int id;

  @Temporal(TemporalType.DATE)
  @Column(name = "order_date")
  private LocalDate orderDate;
  private String status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItems> orderItems = new ArrayList<>();


  public Orders() {
  } // 기본생성자 - JPA에서 사용됨

  public Orders(LocalDate orderDate, String status) {
    this.orderDate = orderDate;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDate orderDate) {
    this.orderDate = orderDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<OrderItems> getOrderItems() {
    return orderItems;
  }

  public void addOrderItem(OrderItems orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  @Override
  public String toString() {
    return "Orders{" +
        "id=" + id +
        ", orderDate=" + orderDate +
        ", status=" + status +
        '}';
  }
}
