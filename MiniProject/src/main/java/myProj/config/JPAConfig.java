package myProj.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import myProj.repository.OrderRepository;
import myProj.repository.OrderRepositoryImpl;
import myProj.repository.ProductRepository;
import myProj.repository.ProductRepositoryImpl;
import myProj.repository.StockRepository;
import myProj.repository.StockRepositoryImpl;
import myProj.service.OrderService;
import myProj.service.OrderServiceImpl;
import myProj.service.ProductService;
import myProj.service.ProductServiceImpl;
import myProj.service.StockService;
import myProj.service.StockServiceImpl;

public class JPAConfig {

  private static EntityManagerFactory emf;
  private static EntityManager em;

  public static void init() {
    emf = Persistence.createEntityManagerFactory("cafeDB");
    em = emf.createEntityManager();
  }

  // Repository 생성
  public static OrderRepository orderRepository() {
    return new OrderRepositoryImpl(em);
  }

  public static ProductRepository productRepository() {
    return new ProductRepositoryImpl(em);
  }

  public static StockRepository stockRepository() {
    return new StockRepositoryImpl(em);
  }

  // Service 생성
  public static OrderService orderService() {
    return new OrderServiceImpl(
        orderRepository(),
        productRepository(),
        stockRepository(),
        em
    );
  }

  public static ProductService productService() {
    return new ProductServiceImpl(
        productRepository(),
        em
    );
  }

  public static StockService stockService() {
    return new StockServiceImpl(
        productRepository(),
        stockRepository(),
        em
    );
  }

  public static void close() {
    if (em != null && em.isOpen()) {
      em.close();
    }
    if (emf != null && emf.isOpen()) {
      emf.close();
    }
  }
}
