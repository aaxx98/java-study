package myProj.dto;

public class Product {

  public int id;
  public String name;
  public String category;
  public boolean stockManage;
  public int price;

  public Product(int id, String name, String category, int price) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
  }

  public void setStockManage(boolean stockManage) {
    this.stockManage = stockManage;
  }
}
