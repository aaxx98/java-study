package com.junit.test.controller;

import com.junit.test.domain.User;
import com.junit.test.dto.UserCreateRequest;
import com.junit.test.dto.UserResponse;
import com.junit.test.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public UserResponse createUser(@RequestBody UserCreateRequest request) {
    return userService.createUser(request);
  }

  @GetMapping("/{id}")
  public UserResponse getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  @PostMapping("/login")
  public UserResponse login(
      @RequestParam String email,
      @RequestParam String password,
      HttpSession session) {
    User user = userService.login(email, password);

    session.setAttribute("user", user);

    return new UserResponse(user);
  }
}