package myProj.dto;

public record StockDTO(
    int id,
    int quantity,
    int productId,
    String productName
) {

}