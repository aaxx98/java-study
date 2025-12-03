package com.junit.test.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 20)
  private String name;

  @Column(nullable = false)
  private String password;

  private LocalDateTime createdAt;

  protected User() {
  }

  public User(String email, String name) {
    this.email = email;
    this.name = name;
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }


  public User(String email, String name, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
