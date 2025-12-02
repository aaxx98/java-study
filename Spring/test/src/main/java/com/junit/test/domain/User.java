package com.junit.test.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 20)
  private String name;

  private LocalDateTime createdAt;

  protected User() {}

  public User(String email, String name) {
    this.email = email;
    this.name = name;
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() { return id; }
  public String getEmail() { return email; }
  public String getName() { return name; }
  public LocalDateTime getCreatedAt() { return createdAt; }
}
