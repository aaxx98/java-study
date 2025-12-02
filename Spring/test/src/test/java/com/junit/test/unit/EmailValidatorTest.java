package com.junit.test.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.junit.test.validator.EmailValidator;
import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

  @Test
  void 이메일이_null이면_예외() {
    assertThrows(IllegalArgumentException.class,
        () -> EmailValidator.validate(null));
  }

  @Test
  void 이메일형식_정상이면_통과() {
    assertDoesNotThrow(() -> EmailValidator.validate("test@test.com"));
    assertDoesNotThrow(() -> EmailValidator.validate("test@."));
  }

  @Test
  void 이메일형식_잘못되면_예외() {
    assertThrows(IllegalArgumentException.class,
        () -> EmailValidator.validate("hello"));
  }
}
