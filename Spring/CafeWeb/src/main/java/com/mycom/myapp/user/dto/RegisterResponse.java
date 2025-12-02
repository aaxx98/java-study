package com.mycom.myapp.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {

  private String name;
  private String email;
  private String status;
}
