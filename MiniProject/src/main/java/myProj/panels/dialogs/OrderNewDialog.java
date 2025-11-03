package myProj.panels.dialogs;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import myProj.dto.OrderItemDetailDTO;
import myProj.dto.ProductDTO;
import myProj.service.OrderService;

public class OrderNewDialog extends JDialog {

  private final JTable itemTable;
  private final DefaultTableModel tableModel;
  private final JLabel totalLabel;
  private final OrderService orderService;

  public OrderNewDialog(
      OrderService orderService,
      JFrame parent
  ) {
    super(parent, "새 주문 추가", true);
    this.orderService = orderService;

    setSize(600, 400);
    setLocationRelativeTo(parent);
    setLayout(new BorderLayout(10, 10));

    // 테이블 (상품명, 수량, 가격, +/− 버튼)
    tableModel = new DefaultTableModel(new String[]{"상품 번호", "상품명", "수량", "단가", "합계", "추가", "감소"},
        0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    itemTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(itemTable);
    add(scrollPane, BorderLayout.CENTER);

    loadProducts();

    JPanel bottomPanel = new JPanel(new BorderLayout());
    totalLabel = new JLabel("총 합계: 0원");
    JButton saveBtn = new JButton("주문 저장");
    bottomPanel.add(totalLabel, BorderLayout.WEST);
    bottomPanel.add(saveBtn, BorderLayout.EAST);
    add(bottomPanel, BorderLayout.SOUTH);

    saveBtn.addActionListener(e -> saveOrder());

    // 테이블 버튼 클릭 이벤트 처리
    itemTable.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = itemTable.rowAtPoint(evt.getPoint());
        int col = itemTable.columnAtPoint(evt.getPoint());
        if (row >= 0) {
          if (col == 5) { // + 버튼
            int qty = (int) tableModel.getValueAt(row, 2);
            qty++;
            tableModel.setValueAt(qty, row, 2);
            updateRowTotal(row);
            updateTotalPrice();
          } else if (col == 6) { // − 버튼
            int qty = (int) tableModel.getValueAt(row, 2);
            if (qty > 0) {
              qty--;
              tableModel.setValueAt(qty, row, 2);
              updateRowTotal(row);
              updateTotalPrice();
            }
          }
        }
      }
    });
  }

  private void loadProducts() {
    List<ProductDTO> products = orderService.getAllProducts();
    tableModel.setRowCount(0);
    for (ProductDTO p : products) {
      tableModel.addRow(new Object[]{p.id(), p.name(), 0, p.price(), 0, "+", "−"});
    }
  }

  private void updateRowTotal(int row) {
    int qty = (int) tableModel.getValueAt(row, 2);
    int price = (int) tableModel.getValueAt(row, 3);
    tableModel.setValueAt(qty * price, row, 4);
  }

  private void updateTotalPrice() {
    int total = 0;
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      total += (int) tableModel.getValueAt(i, 4);
    }
    totalLabel.setText("총 합계: " + total + "원");
  }

  private void saveOrder() {
    List<OrderItemDetailDTO> items = new java.util.ArrayList<>();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
      int qty = (int) tableModel.getValueAt(i, 2);
      if (qty > 0) {
        int id = (int) tableModel.getValueAt(i, 0);
        String name = (String) tableModel.getValueAt(i, 1);
        int price = (int) tableModel.getValueAt(i, 3);
        items.add(new OrderItemDetailDTO(id, name, qty, price));
      }
    }

    if (items.isEmpty()) {
      JOptionPane.showMessageDialog(this, "상품을 1개 이상 선택하세요.");
      return;
    }

    orderService.createOrder(items);
    dispose();
  }
}

