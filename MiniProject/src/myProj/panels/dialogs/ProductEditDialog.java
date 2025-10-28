package myProj.panels.dialogs;

import javax.swing.*;
import java.awt.*;

import myProj.model.Product;

public class ProductEditDialog extends JDialog {

  private final JTextField nameField;
  private final JTextField categoryField;
  private final JTextField priceField;
  private Product product;

  public ProductEditDialog(JFrame parent, Product p) {
    super(parent, "상품 수정", true);
    setSize(400, 250);
    setLocationRelativeTo(parent);
    setLayout(new GridLayout(5, 2, 10, 10));

    this.product = p;

    add(new JLabel("상품명:"));
    nameField = new JTextField(p.name);
    add(nameField);

    add(new JLabel("카테고리:"));
    categoryField = new JTextField(p.category);
    add(categoryField);

    add(new JLabel("가격:"));
    priceField = new JTextField(Integer.toString(p.price));
    add(priceField);

    JButton editButton = new JButton("수정");
    JButton cancelButton = new JButton("취소");
    add(editButton);
    add(cancelButton);

    editButton.addActionListener(e -> {
      try {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String price = priceField.getText().trim();

        if (name.isEmpty() || category.isEmpty() || price.isEmpty()) {
          JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
          return;
        }

        product = new Product(p.id, name, category, Integer.parseInt(price));
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