package myProj.dto;

public record OrderItemDetailDTO(
    int productId,
    String productName,
    int quantity,
    int price
) {

}