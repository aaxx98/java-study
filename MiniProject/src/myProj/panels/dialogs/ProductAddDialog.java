package myProj.panels.dialogs;

import javax.swing.*;
import java.awt.*;

import myProj.model.Product;

public class ProductAddDialog extends JDialog {

  private final JTextField nameField;
  private final JTextField categoryField;
  private final JTextField priceField;
  private Product product;

  public ProductAddDialog(JFrame parent) {
    super(parent, "상품 추가", true);
    setSize(400, 250);
    setLocationRelativeTo(parent);
    setLayout(new GridLayout(5, 2, 10, 10));

    add(new JLabel("상품명:"));
    nameField = new JTextField();
    add(nameField);

    add(new JLabel("카테고리:"));
    categoryField = new JTextField();
    add(categoryField);

    add(new JLabel("가격:"));
    priceField = new JTextField();
    add(priceField);

    JButton addButton = new JButton("추가");
    JButton cancelButton = new JButton("취소");
    add(addButton);
    add(cancelButton);

    addButton.addActionListener(e -> {
      try {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String price = priceField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || price.isEmpty()) {
          JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
          return;
        }

        product = new Product(0, name, category, Integer.parseInt(price));
        dispose();
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "가격은 숫자여야 합니다.");
      }
    });

    cancelButton.addActionListener(e -> {
      product = null;
      dispose();
    });
  }

  public Product getProduct() {
    return product;
  }
}
