package myProj.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Stocks {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @OneToOne
  @JoinColumn(name = "product_id")
  private Products product;

  private int quantity;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public void decrease(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("감소 수량은 0보다 커야 합니다.");
    }
    if (this.quantity < amount) {
      throw new IllegalStateException(
          String.format("재고가 부족합니다. (요청: %d, 현재: %d)", amount, this.quantity)
      );
    }

    this.quantity -= amount;
  }

  public void increase(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("증가 수량은 0보다 커야 합니다.");
    }

    this.quantity += amount;
  }

  @Override
  public String toString() {
    return "Stocks{" +
        "id=" + id +
        ", product=" + product +
        ", quantity=" + quantity +
        '}';
  }
}
