package com.junit.test.validator;

public class EmailValidator {

  public static void validate(String email) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다.");
    }

    if (!email.contains("@") || !email.contains(".")) {
      throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
    }
  }
}
