package myProj;

import javax.swing.*;
import myProj.config.JPAConfig;
import myProj.frames.MainFrame;

public class Main {

  public static void main(String[] args) {
    JPAConfig.init(); // JPA 관련 의존성 관리
    // 애플리케이션 종료 시 리소스 정리
    Runtime.getRuntime().addShutdownHook(new Thread(JPAConfig::close));

    SwingUtilities.invokeLater(() -> {
      new MainFrame().setVisible(true);
    });
  }
}