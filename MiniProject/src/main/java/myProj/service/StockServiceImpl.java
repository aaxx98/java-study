package myProj.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import myProj.domain.Products;
import myProj.domain.Stocks;
import myProj.dto.ProductDTO;
import myProj.dto.StockDTO;
import myProj.repository.ProductRepository;
import myProj.repository.StockRepository;

public class StockServiceImpl implements StockService {

  private final ProductRepository productRepository;
  private final StockRepository stockRepository;
  private final EntityManager em;


  public StockServiceImpl(
      ProductRepository productRepository,
      StockRepository stockRepository,
      EntityManager em) {
    this.productRepository = productRepository;
    this.stockRepository = stockRepository;
    this.em = em;
  }

  @Override
  public List<StockDTO> getAllStocks() {
    List<Stocks> stocks = stockRepository.findAll();
    return stocks.stream()
        .map(order -> new StockDTO(
            order.getId(),
            order.getQuantity(),
            order.getProduct().getId(),
            order.getProduct().getName()
        ))
        .collect(Collectors.toList());

  }

  @Override
  public void addStock(int productId, int amount) {
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();

      // 1. 상품 조회
      Products product = productRepository.findById(productId)
          .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. Product ID: " + productId));

      // 2. 상품 재고 조회 - 있으면 amount 변경, 없으면 생성
      Optional<Stocks> optionalStock = stockRepository.findByProductId(productId);
      Stocks stock;
      if (optionalStock.isPresent()) {
        stock = optionalStock.get();
        stock.setQuantity(stock.getQuantity() + amount);
      } else {
        stock = new Stocks();
        stock.setProduct(product);
        stock.setQuantity(amount);
      }
      stockRepository.save(stock);

      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("재고 변경 중 오류 발생", e);
    }
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
  public void updateStockManage(int productId, boolean stockManage) {
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      // 1. 상품 조회
      Products product = productRepository.findById(productId)
          .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. ID: " + productId));

      // 2. 상품 재고 관리 여부 업데이트
      product.setStockManage(stockManage);
      productRepository.save(product);

      // 3. stockManage가 true이면 재고 확인 후 없으면 생성
      if (stockManage && stockRepository.findExistStock(productId) == 0) {
        Stocks newStock = new Stocks();
        newStock.setProduct(product);
        newStock.setQuantity(0);
        stockRepository.save(newStock);
      }

      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("재고 관리 여부 변경 중 오류 발생", e);
    }
  }
}

