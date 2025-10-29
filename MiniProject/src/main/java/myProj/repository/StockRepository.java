package myProj.repository;

import java.util.Optional;
import myProj.domain.Stocks;

public interface StockRepository {

  Optional<Stocks> findByProductId(int productId);
}
