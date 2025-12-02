package com.junit.test.dto;

public class UserCreateRequest {

  private String email;
  private String name;

  protected UserCreateRequest() {}

  public UserCreateRequest(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public String getEmail() { return email; }
  public String getName() { return name; }
}