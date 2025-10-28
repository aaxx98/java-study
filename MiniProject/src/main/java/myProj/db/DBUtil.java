package myProj.db;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.*;
import java.sql.SQLException;

public class DBUtil {
  
  // SQLException 처리
  public static void showSQLException(SQLException e, String action) {
    String msg = action + " 중 DB 오류가 발생했습니다:\n" + e.getMessage();
    JOptionPane.showMessageDialog(null, msg, action + " 실패", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
  }

  // 외래키 제약 위반 전용 처리
  public static void showFKViolation(SQLIntegrityConstraintViolationException e, String tableName) {
    JOptionPane.showMessageDialog(null,
        "이 항목은 다른 데이터에서 참조 중이므로 삭제할 수 없습니다." + "[" + tableName + "]",
        "삭제 실패",
        JOptionPane.WARNING_MESSAGE);
    e.printStackTrace();
  }
}
