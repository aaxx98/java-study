package myProj.panels.dialogs;

import javax.swing.*;
import java.awt.*;
import myProj.dto.ProductDTO;

public class ProductDeleteDialog extends JDialog {

  private boolean confirmed = false; // 삭제 여부 플래그

  public ProductDeleteDialog(JFrame parent, ProductDTO product) {
    super(parent, "상품 삭제", true);
    setSize(350, 200);
    setLayout(new BorderLayout(10, 10));
    setLocationRelativeTo(parent);

    // 상품 정보 표시
    JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    infoPanel.add(new JLabel("상품명: " + product.name()));
    infoPanel.add(new JLabel("카테고리: " + product.category()));
    infoPanel.add(new JLabel("가격: " + product.price() + "원"));

    // 안내 문구
    JLabel confirmLabel = new JLabel("정말로 삭제하시겠습니까?");
    confirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
    confirmLabel.setFont(confirmLabel.getFont().deriveFont(Font.BOLD));

    // 버튼 영역
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    JButton deleteBtn = new JButton("삭제");
    JButton cancelBtn = new JButton("취소");

    buttonPanel.add(deleteBtn);
    buttonPanel.add(cancelBtn);

    add(infoPanel, BorderLayout.CENTER);
    add(confirmLabel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

    // 이벤트 처리
    deleteBtn.addActionListener(e -> {
      confirmed = true;   // 삭제 확정
      dispose();
    });

    cancelBtn.addActionListener(e -> {
      confirmed = false;  // 취소
      dispose();
    });
  }

  public boolean isConfirmed() {
    return confirmed;
  }
}
