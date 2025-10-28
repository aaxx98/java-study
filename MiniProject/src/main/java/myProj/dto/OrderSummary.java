package myProj.dto;

import java.sql.Date;

public class OrderSummary {

  public int id;
  public Date orderDate;
  public String status;
  public int itemCount;
  public int totalPrice;

  public OrderSummary(int id, Date orderDate, String status, int itemCount, int totalPrice) {
    this.id = id;
    this.orderDate = orderDate;
    this.status = status;
    this.itemCount = itemCount;
    this.totalPrice = totalPrice;
  }

  public String getStatusString() {
    return switch (this.status) {
      case "ORDER" -> "주문";
      case "PREPARE" -> "준비중";
      case "COMPLETE" -> "완료";
      default -> "";
    };
  }
}
