package myProj.model;

public class Stock {

  public int id;
  public int quantity;
  private final int productId;
  private final String productName;

  public Stock(int id, int quantity, int productId, String productName) {
    this.id = id;
    this.quantity = quantity;
    this.productId = productId;
    this.productName = productName;
  }

  public int getProductId() {
    return this.productId;
  }

  public String getProductName() {
    return productName;
  }
}
