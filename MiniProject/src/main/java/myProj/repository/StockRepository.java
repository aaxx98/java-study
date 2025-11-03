package myProj.repository;

import java.util.List;
import java.util.Optional;
import myProj.domain.Stocks;

public interface StockRepository {

  List<Stocks> findAll();

  Optional<Stocks> findByProductId(int productId);

  int findExistStock(int productId);

  void save(Stocks stock);
}
