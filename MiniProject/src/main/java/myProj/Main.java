package myProj;

import javax.swing.*;
import myProj.frames.MainFrame;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new MainFrame().setVisible(true);
    });
  }
}