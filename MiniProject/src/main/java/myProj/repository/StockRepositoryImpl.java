package myProj.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import myProj.domain.Stocks;

public class StockRepositoryImpl implements StockRepository {

  private final EntityManager em;

  public StockRepositoryImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public Optional<Stocks> findByProductId(int productId) {
    List<Stocks> list = em.createQuery(
            "SELECT s FROM Stocks s WHERE s.product.id = :productId", Stocks.class)
        .setParameter("productId", productId)
        .getResultList();
    return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }
}
