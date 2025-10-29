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
}
