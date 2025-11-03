package myProj.dto;

public record ProductDTO(
    int id,
    String name,
    String category,
    int price,
    boolean stockManage
) {

}
