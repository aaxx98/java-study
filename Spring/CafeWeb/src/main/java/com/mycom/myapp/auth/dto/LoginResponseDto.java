package com.mycom.myapp.auth.dto;

import com.mycom.myapp.user.dto.ResponseRegisterDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

  private String accessToken;
  private ResponseRegisterDto user;

  public LoginResponseDto(String accessToken) {
    this.accessToken = accessToken;
  }
}
