package com.mycom.myapp.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

  private int status;
  private String error;
  private String message;
  private String timestamp;
  private String path;

  public ErrorResponse(int status, String error, String message, String path) {
    this.status = status;
    this.error = error;
    this.message = message;
    this.timestamp = java.time.LocalDateTime.now().toString();
    this.path = path;
  }
}
