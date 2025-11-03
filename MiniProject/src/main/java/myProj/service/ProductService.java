package myProj.service;

import java.util.List;
import myProj.dto.ProductDTO;

public interface ProductService {

  List<ProductDTO> searchProducts(String name, String category);

  void createProduct(ProductDTO product);

  void updateProduct(ProductDTO product);

  void deleteProduct(int id);
}
