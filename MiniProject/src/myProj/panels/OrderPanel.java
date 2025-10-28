package myProj.panels;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import myProj.dao.OrderDAO;
import myProj.model.OrderSummary;
import myProj.panels.dialogs.OrderDetailDialog;
import myProj.panels.dialogs.OrderNewDialog;

public class OrderPanel extends JPanel {

  private final JTable table;
  private final DefaultTableModel model;
  private final JLabel dateLabel;
  private LocalDate currentDate;
  private final OrderDAO orderDAO = new OrderDAO();
  private List<OrderSummary> list;

  public OrderPanel() {
    setLayout(new BorderLayout());
    currentDate = LocalDate.now();

    JPanel datePanel = new JPanel(new FlowLayout());
    JButton prevBtn = new JButton("◀");
    JButton nextBtn = new JButton("▶");
    dateLabel = new JLabel(currentDate.format(DateTimeFormatter.ISO_DATE));

    prevBtn.addActionListener(e -> changeDate(-1));
    nextBtn.addActionListener(e -> changeDate(1));

    datePanel.add(prevBtn);
    datePanel.add(dateLabel);
    datePanel.add(nextBtn);

    add(datePanel, BorderLayout.NORTH);

    model = new DefaultTableModel(new String[]{"ID", "상태", "상품개수", "가격 합계"}, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table = new JTable(model);
    add(new JScrollPane(table), BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout());
    JButton newOrderBtn = new JButton("신규 주문");
    JButton detailBtn = new JButton("상세보기");
    JButton refreshBtn = new JButton("새로고침");

    btnPanel.add(newOrderBtn);
    btnPanel.add(detailBtn);
    btnPanel.add(refreshBtn);
    add(btnPanel, BorderLayout.SOUTH);

    newOrderBtn.addActionListener(e -> openNewOrderDialog());
    refreshBtn.addActionListener(e -> loadOrders());
    detailBtn.addActionListener(e -> openOrderDetail());

    loadOrders();
  }

  private void changeDate(int days) {
    currentDate = currentDate.plusDays(days);
    dateLabel.setText(currentDate.format(DateTimeFormatter.ISO_DATE));
    loadOrders();
  }

  private void loadOrders() {
    model.setRowCount(0);
    this.list = orderDAO.getOrdersByDate(currentDate);
    for (OrderSummary summary : this.list) {
      model.addRow(new Object[]{
          summary.id,
          summary.getStatusString(),
          summary.itemCount,
          summary.totalPrice
      });
    }
  }

  private void openNewOrderDialog() {
    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    OrderNewDialog dialog = new OrderNewDialog(parentFrame);
    dialog.setVisible(true);
    loadOrders();
  }

  private void openOrderDetail() {
    int row = table.getSelectedRow();
    if (row == -1) {
      JOptionPane.showMessageDialog(this, "주문을 선택하세요.");
      return;
    }

    OrderDetailDialog dialog = new OrderDetailDialog(
        (JFrame) SwingUtilities.getWindowAncestor(this), this.list.get(row));
    dialog.setVisible(true);
    loadOrders();
  }
}
