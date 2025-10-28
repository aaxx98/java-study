package myProj.dto;

public class OrderItemDetail {

  public int productId;
  public String productName;
  public int quantity;
  public int price;

  public OrderItemDetail(int productId, String productName, int quantity, int price) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
  }
}
