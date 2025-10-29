package myProj.panels;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import myProj.dao.StockDAO;
import myProj.dto.StockDTO;
import myProj.panels.dialogs.ProductStockDialog;

public class StockPanel extends JPanel {

  private final JTable table;
  private final DefaultTableModel tableModel;
  private final StockDAO stockDAO = new StockDAO();
  private List<StockDTO> list;

  public StockPanel() {
    setLayout(new BorderLayout());

    String[] columns = {"상품명", "현재 재고"};
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton editStockProduct = new JButton("재고 관리 여부 변경");
    JButton addBtn = new JButton("입고");
    JButton subtBtn = new JButton("폐기");
    JButton refreshBtn = new JButton("새로고침");

    buttonPanel.add(editStockProduct);
    buttonPanel.add(addBtn);
    buttonPanel.add(subtBtn);
    buttonPanel.add(refreshBtn);
    add(buttonPanel, BorderLayout.SOUTH);

    loadStock();

    refreshBtn.addActionListener(e -> loadStock());
    editStockProduct.addActionListener(e -> editProductStock());
    addBtn.addActionListener(e -> increaseStock());
    subtBtn.addActionListener(e -> decreaseStock());
  }

  private void loadStock() {
    tableModel.setRowCount(0);
    this.list = stockDAO.getAllStocks();
    for (StockDTO s : list) {
      tableModel.addRow(new Object[]{
          s.productName(),
          s.quantity()
      });
    }
  }

  private void editProductStock() {
    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    ProductStockDialog dialog = new ProductStockDialog(parentFrame);
    dialog.setVisible(true);
    loadStock();
  }

  private void increaseStock() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "상품을 선택하세요.");
      return;
    }

    int productId = list.get(row).productId();
    String productName = list.get(row).productName();
    String input = JOptionPane.showInputDialog(this, productName + " 재고 수량 추가:", "0");
    if (input != null) {
      try {
        int amount = Integer.parseInt(input);
        if (amount <= 0) {
          throw new NumberFormatException();
        }
        stockDAO.addStock(productId, amount);
        loadStock();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "올바른 숫자를 입력하세요.");
      }
    }
  }

  private void decreaseStock() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "상품을 선택하세요.");
      return;
    }

    int productId = list.get(row).productId();
    String productName = list.get(row).productName();
    String input = JOptionPane.showInputDialog(this, productName + " 재고 수량 감소:", "0");
    if (input != null) {
      try {
        int amount = Integer.parseInt(input);
        if (amount <= 0) {
          throw new NumberFormatException();
        }
        stockDAO.subtractStock(productId, amount);
        loadStock();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "올바른 숫자를 입력하세요.");
      }
    }
  }
}