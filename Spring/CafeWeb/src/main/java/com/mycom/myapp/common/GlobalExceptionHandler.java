package com.mycom.myapp.common;

import com.mycom.myapp.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public
class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class) // 모든 예외
  public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex,
      HttpServletRequest request) {
    System.out.println("예외 처리 발생...");
    ex.printStackTrace(); // 로그 남기기
    ErrorResponse response = new ErrorResponse(
        500,
        "Internal Server Error",
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
