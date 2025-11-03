package myProj.dto;

import java.time.LocalDate;

public record OrderSummaryDTO(
    int id,
    LocalDate orderDate,
    String status,
    int itemCount,
    int totalPrice
) {

  public String getStatusString() {
    return switch (this.status) {
      case "ORDER" -> "주문";
      case "PREPARE" -> "준비중";
      case "COMPLETE" -> "완료";
      default -> "";
    };
  }
}
