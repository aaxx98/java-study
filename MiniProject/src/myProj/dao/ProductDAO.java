package myProj.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import myProj.db.DBConnection;
import myProj.model.Product;
import myProj.db.DBUtil;

public class ProductDAO {

  public List<Product> getAllProducts(String name, String category) {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT * FROM Products WHERE name LIKE ? AND category LIKE ?";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, "%" + name + "%");
      ps.setString(2, "%" + category + "%");

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          Product p = new Product(
              rs.getInt("id"),
              rs.getString("name"),
              rs.getString("category"),
              rs.getInt("price")
          );
          p.setStockManage(rs.getBoolean("stock_manage"));
          products.add(p);
        }
      }

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "상품 조회");
    }
    return products;
  }

  public void addProduct(Product p) {
    String sql = "INSERT INTO Products(name, category, price) VALUES (?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setString(1, p.name);
      ps.setString(2, p.category);
      ps.setInt(3, p.price);
      ps.executeUpdate();

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "상품 추가");
    }
  }

  public void editProduct(Product p) {
    String sql = "UPDATE Products SET name = ?, category = ?, price = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setString(1, p.name);
      ps.setString(2, p.category);
      ps.setInt(3, p.price);
      ps.setInt(4, p.id);
      ps.executeUpdate();

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "상품 수정");
    }
  }

  public void deleteProduct(int id) {
    String sql = "DELETE FROM Products WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, id);
      ps.executeUpdate();

    } catch (SQLIntegrityConstraintViolationException e) {
      DBUtil.showFKViolation(e, "Products");
    } catch (SQLException e) {
      DBUtil.showSQLException(e, "상품 삭제");
    }
  }

  // 재고 관리 여부 변경
  public void updateStockManage(int productId, boolean stockManage) {
    String updateProductSql = "UPDATE Products SET stock_manage = ? WHERE id = ?";
    String checkStockSql = "SELECT COUNT(*) FROM Stocks WHERE product_id = ?";
    String insertStockSql = "INSERT INTO Stocks(product_id, quantity) VALUES (?, 0)";

    Connection conn = null;
    try {
      conn = DBConnection.getConnection();
      conn.setAutoCommit(false); // 트랜잭션 시작

      // 1. Product 테이블 업데이트
      try (PreparedStatement ps = conn.prepareStatement(updateProductSql)) {
        ps.setBoolean(1, stockManage);
        ps.setInt(2, productId);
        int updatedRows = ps.executeUpdate();
        if (updatedRows == 0) {
          System.out.println("해당 Product_id가 존재하지 않습니다.");
        }
      }

      // 2. Stock 존재 여부 확인
      boolean stockExists = false;
      try (PreparedStatement psCheck = conn.prepareStatement(checkStockSql)) {
        psCheck.setInt(1, productId);
        try (ResultSet rs = psCheck.executeQuery()) {
          if (rs.next() && rs.getInt(1) > 0) {
            stockExists = true;
          }
        }
      }

      // 3. Stock이 없으면 생성
      if (!stockExists) {
        try (PreparedStatement psInsert = conn.prepareStatement(insertStockSql)) {
          psInsert.setInt(1, productId);
          psInsert.executeUpdate();
        }
      }

      conn.commit(); // 트랜잭션 커밋
    } catch (SQLException e) {
      if (conn != null) {
        try {
          conn.rollback(); // 오류 시 롤백
        } catch (SQLException rollbackEx) {
          rollbackEx.printStackTrace();
        }
      }
      DBUtil.showSQLException(e, "재고 관리 여부 변경");
    } finally {
      if (conn != null) {
        try {
          conn.setAutoCommit(true); // 원래 상태로 복구
          conn.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    }
  }
}
