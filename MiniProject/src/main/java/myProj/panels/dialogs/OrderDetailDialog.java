package myProj.panels.dialogs;

import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import myProj.dto.*;
import myProj.service.OrderService;

public class OrderDetailDialog extends JDialog {

  private final int orderId;
  private final OrderService orderService;

  private JComboBox<String> statusCombo;
  private JTable itemTable;
  private DefaultTableModel tableModel;

  public OrderDetailDialog(
      OrderService orderService,
      JFrame parent,
      OrderSummaryDTO orderSummary
  ) {
    super(parent, "주문 상세보기", true);
    this.orderService = orderService;
    this.orderId = orderSummary.id();

    setSize(500, 400);
    setLocationRelativeTo(parent);
    setLayout(new BorderLayout(10, 10));

    JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));

    topPanel.add(new JLabel("주문 ID:"));
    topPanel.add(new JLabel(String.valueOf(orderSummary.id())));
    topPanel.add(new JLabel("주문 날짜:"));
    topPanel.add(new JLabel(orderSummary.orderDate().toString()));
    topPanel.add(new JLabel("주문 상태:"));
    topPanel.add(new JLabel(orderSummary.getStatusString()));
    topPanel.add(new JLabel("가격 합계:"));
    topPanel.add(new JLabel(String.valueOf(orderSummary.totalPrice())));

    add(topPanel, BorderLayout.NORTH);

    tableModel = new DefaultTableModel(new String[]{"상품명", "수량", "가격"}, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    itemTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(itemTable);
    add(scrollPane, BorderLayout.CENTER);

    loadOrderItems();

    JPanel bottomPanel = new JPanel(new BorderLayout());

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton saveBtn = new JButton("상태 저장");
    JButton closeBtn = new JButton("닫기");
    btnPanel.add(saveBtn);
    btnPanel.add(closeBtn);

    JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    statusPanel.add(new JLabel("상태:"));

    Map<String, String> statusMap = new LinkedHashMap<>();
    statusMap.put("ORDER", "주문");
    statusMap.put("PREPARE", "준비중");
    statusMap.put("COMPLETE", "완료");

    statusCombo = new JComboBox<>(statusMap.values().toArray(new String[0]));
    for (Map.Entry<String, String> entry : statusMap.entrySet()) {
      if (entry.getKey().equals(orderSummary.status())) {
        statusCombo.setSelectedItem(statusMap.get(orderSummary.status()));
        break;
      }
    }
    statusPanel.add(statusCombo);

    bottomPanel.add(statusPanel, BorderLayout.NORTH);
    bottomPanel.add(btnPanel, BorderLayout.SOUTH);
    add(bottomPanel, BorderLayout.SOUTH);

    saveBtn.addActionListener(e -> saveStatus());
    closeBtn.addActionListener(e -> dispose());
  }

  private void loadOrderItems() {
    List<OrderItemDetailDTO> items = orderService.getOrderItems(orderId);
    tableModel.setRowCount(0);
    for (OrderItemDetailDTO item : items) {
      tableModel.addRow(new Object[]{
          item.productName(),
          item.quantity(),
          item.price()
      });
    }
  }

  private void saveStatus() {
    String selectedLabel = (String) statusCombo.getSelectedItem();
    String newStatus = null;

    if (selectedLabel.equals("주문")) {
      newStatus = "ORDER";
    } else if (selectedLabel.equals("준비중")) {
      newStatus = "PREPARE";
    } else if (selectedLabel.equals("완료")) {
      newStatus = "COMPLETE";
    }

    orderService.updateOrderStatus(orderId, newStatus);
    dispose();
  }
}
