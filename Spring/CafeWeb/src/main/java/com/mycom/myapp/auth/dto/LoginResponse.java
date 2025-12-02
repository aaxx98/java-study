package com.mycom.myapp.auth.dto;

import com.mycom.myapp.user.dto.RegisterResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

  private String accessToken;
  private RegisterResponse user;

  public LoginResponse(String accessToken) {
    this.accessToken = accessToken;
  }
}
