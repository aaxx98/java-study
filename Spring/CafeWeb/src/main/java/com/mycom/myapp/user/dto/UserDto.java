package com.mycom.myapp.user.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private int id;
  private String name;
  private String email;
  private String password;
  private LocalDate createdAt;
  private LocalDate updatedAt;
}
