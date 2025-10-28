package myProj.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import myProj.db.DBConnection;
import myProj.model.*;
import myProj.db.DBUtil;

public class OrderDAO {

  public List<OrderSummary> getOrdersByDate(LocalDate date) {
    List<OrderSummary> list = new ArrayList<>();

    String sql = """
        SELECT o.id, o.order_date, o.status, SUM(oi.quantity) AS item_count, SUM(oi.price * oi.quantity) AS total_price
        FROM Orders o
        JOIN OrderItems oi ON o.id = oi.order_id
        WHERE DATE(o.order_date) = ?
        GROUP BY o.id, o.order_date, o.status
        ORDER BY o.id DESC
        """;

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDate(1, Date.valueOf(date));
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        list.add(new OrderSummary(
            rs.getInt("id"),
            rs.getDate("order_date"),
            rs.getString("status"),
            rs.getInt("item_count"),
            rs.getInt("total_price")
        ));
      }
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "주문 조회");
    }

    return list;
  }

  public List<OrderItemDetail> getOrderItems(int orderId) {
    List<OrderItemDetail> list = new ArrayList<>();

    String sql = """
        SELECT p.id AS product_id, p.name AS product_name, oi.quantity, oi.price
        FROM OrderItems oi
        JOIN Products p ON oi.product_id = p.id
        WHERE oi.order_id = ?
        """;

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, orderId);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        list.add(new OrderItemDetail(
            rs.getInt("product_id"),
            rs.getString("product_name"),
            rs.getInt("quantity"),
            rs.getInt("price")
        ));
      }
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "주문 상세 조회");
    }
    return list;
  }

  public void updateStatus(int orderId, String status) {
    String sql = "UPDATE Orders SET status = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setString(1, status);
      ps.setInt(2, orderId);
      ps.executeUpdate();
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "주문 상태 변경");
    }
  }

  public List<Product> getAllProducts() {
    List<Product> products = new java.util.ArrayList<>();
    String sql = "SELECT id, name, category, price FROM Products";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        products.add(new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("category"),
            rs.getInt("price")
        ));
      }
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "주문 상품 조회");
    }
    return products;
  }

  public void insertOrder(List<OrderItemDetail> items) {
    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);

      int orderId;

      // 주문 생성
      String orderSql = "INSERT INTO Orders(order_date) VALUES(CURDATE())";
      try (PreparedStatement ps = conn.prepareStatement(orderSql,
          Statement.RETURN_GENERATED_KEYS)) {
        ps.executeUpdate();
        try (ResultSet rs = ps.getGeneratedKeys()) {
          if (rs.next()) {
            orderId = rs.getInt(1);
          } else {
            throw new SQLException("주문 ID 생성 실패");
          }
        }
      }

      // 주문 항목 추가
      String itemSql = "INSERT INTO OrderItems(order_id, product_id, quantity, price) VALUES(?, ?, ?, ?)";
      try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
        for (OrderItemDetail item : items) {
          ps.setInt(1, orderId);
          ps.setInt(2, item.productId);
          ps.setInt(3, item.quantity);
          ps.setInt(4, item.price);
          ps.addBatch();
        }
        ps.executeBatch();
      }

      // 재고 감소 처리
      String stockCheckSql = "SELECT stock_manage FROM Products WHERE id = ?";
      String stockUpdateSql = "UPDATE Stocks SET quantity = quantity - ? WHERE product_id = ?";
      try (PreparedStatement checkPs = conn.prepareStatement(stockCheckSql);
          PreparedStatement updatePs = conn.prepareStatement(stockUpdateSql)) {

        for (OrderItemDetail item : items) {
          checkPs.setInt(1, item.productId);
          try (ResultSet rs = checkPs.executeQuery()) {
            if (rs.next() && rs.getInt("stock_manage") == 1) {
              updatePs.setInt(1, item.quantity);
              updatePs.setInt(2, item.productId);
              updatePs.executeUpdate();
            }
          }
        }
      }
      conn.commit();
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "주문 저장 중 오류");
      try {
        // 롤백 추가
        DBConnection.getConnection().rollback();
      } catch (SQLException rollbackEx) {
        System.err.println("롤백 실패: " + rollbackEx.getMessage());
      }
    }
  }
}
