package myProj.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Orders order;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Products product;

  private int quantity;
  private int price;

  public OrderItems() {
  }

  public OrderItems(Orders order, Products product, int quantity, int price) {
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Orders getOrder() {
    return order;
  }

  public void setOrder(Orders order) {
    this.order = order;
  }

  public Products getProduct() {
    return product;
  }

  public void setProduct(Products product) {
    this.product = product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "OrderItems{" +
        "id=" + id +
        ", order=" + order +
        ", product=" + product +
        ", quantity=" + quantity +
        ", price=" + price +
        '}';
  }
}
