package myProj.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import myProj.dto.ProductDTO;
import myProj.panels.dialogs.ProductAddDialog;
import myProj.panels.dialogs.ProductDeleteDialog;
import myProj.panels.dialogs.ProductEditDialog;
import myProj.service.ProductService;

public class ProductPanel extends JPanel {

  private final JTable table;
  private final DefaultTableModel tableModel;
  private final ProductService productService;
  private final JTextField nameField;
  private final JTextField categoryField;


  public ProductPanel(ProductService productService) {
    this.productService = productService;

    setLayout(new BorderLayout());

    String[] columns = {"ID", "상품명", "카테고리", "가격"};
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table = new JTable(tableModel);
    add(new JScrollPane(table), BorderLayout.CENTER);

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    nameField = new JTextField(10);
    categoryField = new JTextField(10);
    JButton searchBtn = new JButton("검색");
    JButton initSearchBtn = new JButton("검색 초기화");

    searchPanel.add(new JLabel("상품명:"));
    searchPanel.add(nameField);
    searchPanel.add(new JLabel("카테고리:"));
    searchPanel.add(categoryField);
    searchPanel.add(searchBtn);
    searchPanel.add(initSearchBtn);

    add(searchPanel, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    JButton addBtn = new JButton("추가");
    JButton editBtn = new JButton("수정");
    JButton deleteBtn = new JButton("삭제");
    JButton refreshBtn = new JButton("새로고침");
    buttonPanel.add(addBtn);
    buttonPanel.add(editBtn);
    buttonPanel.add(deleteBtn);
    buttonPanel.add(refreshBtn);
    add(buttonPanel, BorderLayout.SOUTH);

    loadProducts("", "");

    addBtn.addActionListener(e -> addProduct());
    editBtn.addActionListener(e -> editProduct());
    deleteBtn.addActionListener(e -> deleteProduct());
    refreshBtn.addActionListener(e -> search());
    searchBtn.addActionListener(e -> search());
    initSearchBtn.addActionListener(e -> init());
  }

  private void search() {
    String name = nameField.getText().trim();
    String category = categoryField.getText().trim();
    loadProducts(name, category);
  }

  private void loadProducts(String name, String category) {
    tableModel.setRowCount(0);
    List<ProductDTO> products = productService.searchProducts(name, category);
    for (ProductDTO p : products) {
      tableModel.addRow(new Object[]{
          p.id(),
          p.name(),
          p.category(),
          p.price()
      });
    }
  }


  private void addProduct() {
    ProductAddDialog dialog = new ProductAddDialog((JFrame) SwingUtilities.getWindowAncestor(this));
    dialog.setVisible(true);

    ProductDTO p = dialog.getProduct();
    if (p != null) {
      productService.createProduct(p);
      init();
    }
  }

  private void editProduct() {
    int row = table.getSelectedRow();
    if (row < 0) {
      JOptionPane.showMessageDialog(this, "수정할 상품을 선택하세요.");
      return;
    }
    int id = (int) tableModel.getValueAt(row, 0);
    String name = (String) tableModel.getValueAt(row, 1);
    String category = (String) tableModel.getValueAt(row, 2);
    int price = (int) tableModel.getValueAt(row, 3);
    ProductDTO product = new ProductDTO(id, name, category, price, false);

    ProductEditDialog dialog = new ProductEditDialog(
        (JFrame) SwingUtilities.getWindowAncestor(this), product);
    dialog.setVisible(true);

    ProductDTO p = dialog.getProduct();
    if (p != null) {
      productService.updateProduct(p);
      init();
    }

  }

  private void deleteProduct() {
    int row = table.getSelectedRow();
    if (row >= 0) {
      int id = (int) tableModel.getValueAt(row, 0);
      String name = (String) tableModel.getValueAt(row, 1);
      String category = (String) tableModel.getValueAt(row, 2);
      int price = (int) tableModel.getValueAt(row, 3);
      ProductDTO product = new ProductDTO(id, name, category, price, false);

      ProductDeleteDialog dialog = new ProductDeleteDialog(
          (JFrame) SwingUtilities.getWindowAncestor(this), product);
      dialog.setVisible(true);

      if (dialog.isConfirmed()) {
        productService.deleteProduct(product.id());
        init();
      }
    } else {
      JOptionPane.showMessageDialog(this, "삭제할 상품을 선택하세요.");
    }
  }

  private void init() {
    nameField.setText("");
    categoryField.setText("");
    loadProducts("", "");
  }
}
