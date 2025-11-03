package myProj.service;

import java.util.List;
import myProj.dto.ProductDTO;
import myProj.dto.StockDTO;

public interface StockService {

  List<StockDTO> getAllStocks();

  void addStock(int productId, int amount);
  
  List<ProductDTO> searchProducts(String name, String category);

  void updateStockManage(int productId, boolean stockManage);
}
