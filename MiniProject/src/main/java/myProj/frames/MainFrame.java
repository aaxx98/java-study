package myProj.frames;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import myProj.config.JPAConfig;
import myProj.panels.*;

public class MainFrame extends JFrame {

  public MainFrame() {
    setTitle("카페 관리 프로그램");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("상품관리", new ProductPanel(JPAConfig.productService()));
    tabbedPane.addTab("재고관리", new StockPanel(JPAConfig.stockService()));
    tabbedPane.addTab("주문관리", new OrderPanel(JPAConfig.orderService()));

    add(tabbedPane);
  }
}