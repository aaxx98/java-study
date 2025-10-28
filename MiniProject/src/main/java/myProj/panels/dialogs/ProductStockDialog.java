
package myProj.panels.dialogs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import myProj.dao.ProductDAO;
import myProj.dto.Product;

public class ProductStockDialog extends JDialog {

  private JTable table;
  private DefaultTableModel tableModel;
  private ProductDAO productDAO = new ProductDAO();

  public ProductStockDialog(JFrame parent) {
    super(parent, "재고 관리 여부 변경", true);
    setSize(500, 400);
    setLocationRelativeTo(parent);
    setLayout(new BorderLayout(10, 10));

    // 테이블 컬럼
    String[] columns = {"ID", "상품명", "재고 관리 여부"};
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 2; // '재고 관리 여부'만 수정 가능
      }
    };

    table = new JTable(tableModel);
    table.getColumnModel().getColumn(2)
        .setCellEditor(new DefaultCellEditor(new JComboBox<>(new String[]{"예", "아니오"})));

    add(new JScrollPane(table), BorderLayout.CENTER);

    // 버튼 패널
    JPanel btnPanel = new JPanel(new FlowLayout());
    JButton saveBtn = new JButton("저장");
    JButton cancelBtn = new JButton("취소");
    btnPanel.add(saveBtn);
    btnPanel.add(cancelBtn);
    add(btnPanel, BorderLayout.SOUTH);

    loadProducts();

    // 버튼 이벤트
    saveBtn.addActionListener(e -> saveChanges());
    cancelBtn.addActionListener(e -> dispose());
  }

  private void loadProducts() {
    tableModel.setRowCount(0);
    List<Product> products = productDAO.getAllProducts("", "");
    for (Product p : products) {
      tableModel.addRow(new Object[]{
          p.id,
          p.name,
          p.stockManage ? "예" : "아니오"
      });
    }
  }

  private void saveChanges() {
    try {
      for (int i = 0; i < tableModel.getRowCount(); i++) {
        int id = (int) tableModel.getValueAt(i, 0);
        String value = (String) tableModel.getValueAt(i, 2);
        boolean stockManage = "예".equals(value);
        productDAO.updateStockManage(id, stockManage);
      }
      JOptionPane.showMessageDialog(this, "재고 관리 여부가 저장되었습니다.");
      dispose();
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "저장 중 오류: " + e.getMessage());
    }
  }
}
