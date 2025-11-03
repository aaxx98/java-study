package myProj.repository;

import java.util.List;
import java.util.Optional;
import myProj.domain.Products;

public interface ProductRepository {

  Optional<Products> findById(int id);

  List<Products> findAll();

  List<Products> findProductBySearchCondition(String name, String category);

  void save(Products product);
  
  void delete(int id);
}
