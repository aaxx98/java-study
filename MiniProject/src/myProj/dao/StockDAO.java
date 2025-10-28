package myProj.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import myProj.db.DBConnection;
import myProj.model.Stock;
import myProj.db.DBUtil;

public class StockDAO {

  public List<Stock> getAllStocks() {
    List<Stock> stocks = new ArrayList<>();
    String sql = "SELECT *, Products.id as product_id, Products.name as product_name FROM Stocks "
        + "JOIN Products ON Products.id = Stocks.product_id "
        + "WHERE Products.stock_manage = true";

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        Stock s = new Stock(
            rs.getInt("id"),
            rs.getInt("quantity"),
            rs.getInt("product_id"),
            rs.getString("product_name")
        );
        stocks.add(s);
      }

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "재고 조회");
    }

    return stocks;
  }

  public void addStock(int productId, int amount) {
    String sql = "UPDATE Stocks SET quantity = quantity + ? WHERE product_id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, amount);
      ps.setInt(2, productId);
      ps.executeUpdate();

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "재고 추가");
    }
  }

  public void subtractStock(int productId, int amount) {
    String sql = "UPDATE Stocks SET quantity = quantity - ? WHERE product_id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, amount);
      ps.setInt(2, productId);
      ps.executeUpdate();

    } catch (SQLException e) {
      DBUtil.showSQLException(e, "재고 감소");
    }
  }
}
