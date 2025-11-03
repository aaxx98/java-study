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
  public List<Stocks> findAll() {
    String jpql = "SELECT s FROM Stocks s "
        + "JOIN FETCH s.product p "
        + "WHERE p.stockManage = true";
    return em.createQuery(jpql, Stocks.class).getResultList();
  }

  @Override
  public Optional<Stocks> findByProductId(int productId) {
    List<Stocks> list = em.createQuery(
            "SELECT s FROM Stocks s WHERE s.product.id = :productId", Stocks.class)
        .setParameter("productId", productId)
        .getResultList();
    return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }

  @Override
  public int findExistStock(int productId) {
    String jpql = "SELECT COUNT(s) FROM Stocks s WHERE s.product.id = :productId";
    Long count = em.createQuery(jpql, Long.class)
        .setParameter("productId", productId)
        .getSingleResult();
    return count != null ? count.intValue() : 0;
  }

  @Override
  public void save(Stocks stock) {
    if (stock.getId() == 0) {
      em.persist(stock);
    } else {
      em.merge(stock);
    }
  }
}
