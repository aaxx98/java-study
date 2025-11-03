package myProj.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import myProj.domain.OrderItems;
import myProj.domain.Orders;
import myProj.domain.Products;
import myProj.domain.Stocks;
import myProj.dto.OrderItemDetailDTO;
import myProj.dto.OrderSummaryDTO;
import myProj.dto.ProductDTO;
import myProj.repository.OrderRepository;
import myProj.repository.ProductRepository;
import myProj.repository.StockRepository;

public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;
  private final StockRepository stockRepository;
  private final EntityManager em;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      ProductRepository productRepository,
      StockRepository stockRepository,
      EntityManager em) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.stockRepository = stockRepository;
    this.em = em;
  }

  @Override
  public List<OrderSummaryDTO> getOrdersByDate(LocalDate date) {
    List<Orders> orders = orderRepository.findByOrderDate(date);

    return orders.stream()
        .map(order -> {
          int itemCount = order.getOrderItems().size();
          int totalPrice = order.getOrderItems().stream()
              .mapToInt(item -> item.getPrice() * item.getQuantity())
              .sum();

          return new OrderSummaryDTO(
              order.getId(),
              order.getOrderDate(),
              order.getStatus(),
              itemCount,
              totalPrice
          );
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<ProductDTO> getAllProducts() {
    List<Products> products = productRepository.findAll();
    return products.stream()
        .map(product -> {
          return new ProductDTO(
              product.getId(),
              product.getName(),
              product.getCategory(),
              product.getPrice(),
              product.isStockManage()
          );
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderItemDetailDTO> getOrderItems(Integer orderId) {
    Orders order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다. ID: " + orderId));

    return order.getOrderItems().stream()
        .map(item -> new OrderItemDetailDTO(
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getQuantity(),
            item.getPrice()
        ))
        .collect(Collectors.toList());
  }

  @Override
  public void createOrder(List<OrderItemDetailDTO> items) {
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();

      // 1. 주문 생성
      Orders order = new Orders();
      order.setOrderDate(LocalDate.now());
      order.setStatus("ORDER");

      // 2. 주문 항목 추가
      for (OrderItemDetailDTO dto : items) {
        Products product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        OrderItems orderItem = new OrderItems();
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(dto.price());

        order.addOrderItem(orderItem);

        // 3. 재고 차감 (재고 관리 대상인 경우만)
        if (product.isStockManage()) {
          Stocks stock = stockRepository.findByProductId(product.getId())
              .orElseThrow(() -> new IllegalStateException("재고 정보가 없습니다."));

          stock.decrease(dto.quantity());
        }
      }

      orderRepository.save(order);
      tx.commit();

    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("주문 생성 중 오류 발생", e);
    }
  }

  @Override
  public void updateOrderStatus(Integer orderId, String status) {
    EntityTransaction tx = em.getTransaction();

    try {
      tx.begin();
      orderRepository.updateStatus(orderId, status);
      tx.commit();
    } catch (Exception e) {
      if (tx.isActive()) {
        tx.rollback();
      }
      throw new RuntimeException("주문 상태 변경 중 오류 발생", e);
    }
  }
}
