package myProj.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import myProj.domain.Products;

public class ProductRepositoryImpl implements ProductRepository {

  private final EntityManager em;

  public ProductRepositoryImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public Optional<Products> findById(int id) {
    return Optional.ofNullable(em.find(Products.class, id));
  }

  @Override
  public List<Products> findAll() {
    return em.createQuery("SELECT p FROM Products p", Products.class)
        .getResultList();
  }

  @Override
  public List<Products> findProductBySearchCondition(String name, String category) {
    String jpql = "SELECT p FROM Products p WHERE p.name LIKE :name AND p.category LIKE :category";
    return em.createQuery(jpql, Products.class)
        .setParameter("name", "%" + name + "%")
        .setParameter("category", "%" + category + "%")
        .getResultList();
  }

  @Override
  public void save(Products product) {
    if (product.getId() == 0) {
      em.persist(product);
    } else {
      em.merge(product);
    }
  }

  @Override
  public void delete(int id) {
    Products product = em.find(Products.class, id);
    if (product != null) {
      em.remove(product);
    }
  }
}
