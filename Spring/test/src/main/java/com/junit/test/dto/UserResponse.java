package com.junit.test.dto;


import com.junit.test.domain.User;

public class UserResponse {

  private Long id;
  private String email;
  private String name;

  public UserResponse(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public String getName() { return name; }
}