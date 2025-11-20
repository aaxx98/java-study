package my.jpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;


@Entity
public class Product2 {

  @EmbeddedId
  private Product2Key id;

  private String color;

  public Product2() {
  }

  public Product2(Product2Key id, String color) {
    this.id = id;
    this.color = color;
  }

  public Product2Key getId() {
    return id;
  }

  public void setId(Product2Key id) {
    this.id = id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString() {
    return "Product2{" +
        "id=" + id +
        ", color='" + color + '\'' +
        '}';
  }
}

