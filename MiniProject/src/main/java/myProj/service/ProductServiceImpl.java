package myProj.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.Collectors;
import myProj.domain.Products;
import myProj.dto.ProductDTO;
import myProj.repository.ProductRepository;

public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final EntityManager em;

  public ProductServiceImpl(
      ProductRepository productRepository,
      EntityManager em) {
    this.productRepository = productRepository;
    this.em = em;
  }

  @Override
  public List<ProductDTO> searchProducts(String name, String category) {
    List<Products> products = productRepository.findProductBySearchCondition(name, category);
    return products.stream()
        .map(order -> new ProductDTO(
            order.getId(),
            order.getName(),
            order.getCategory(),
            order.getPrice(),
            order.isStockManage()
        ))
        .collect(Collectors.toList());
  }

  @Override
  public void createProduct(ProductDTO dto) {
    Products saveProduct = new Products();
    saveProduct.setName(dto.name());
    saveProduct.setCategory(dto.category());
    saveProduct.setPrice(dto.price());
    saveProduct.setStockManage(dto.stockManage());

    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      productRepository.save(saveProduct);
      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("신규 상품 등록 중 오류 발생", e);
    }
  }

  @Override
  public void updateProduct(ProductDTO dto) {
    Products updateProduct = new Products();
    updateProduct.setId(dto.id());
    updateProduct.setName(dto.name());
    updateProduct.setCategory(dto.category());
    updateProduct.setPrice(dto.price());
    updateProduct.setStockManage(dto.stockManage());

    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      productRepository.save(updateProduct);
      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("상품 정보 변경 중 오류 발생", e);
    }
  }

  @Override
  public void deleteProduct(int id) {
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      productRepository.delete(id);
      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("상품 정보 삭제 중 오류 발생", e);
    }
  }
}
