package com.mycom.myapp.common.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

  private Long id;
  private String name;
  private String email;
  private String password;
}
