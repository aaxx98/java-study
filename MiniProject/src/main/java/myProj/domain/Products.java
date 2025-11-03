package myProj.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Products {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String name;
  private String category;
  private int price;

  @Column(name = "stock_manage")
  private boolean stockManage;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public void setStockManage(boolean stockManage) {
    this.stockManage = stockManage;
  }

  public boolean isStockManage() {
    return stockManage;
  }

  @Override
  public String toString() {
    return "Products{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", category='" + category + '\'' +
        ", price=" + price +
        ", stockManage=" + stockManage +
        '}';
  }
}
