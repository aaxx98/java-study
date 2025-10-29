package myProj.repository;

import java.util.List;
import java.util.Optional;
import myProj.domain.Products;

public interface ProductRepository {

  Optional<Products> findById(int id);

  List<Products> findAll();

}
