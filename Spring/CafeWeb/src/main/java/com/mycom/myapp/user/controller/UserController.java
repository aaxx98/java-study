package com.mycom.myapp.user.controller;

import com.mycom.myapp.user.dto.RegisterRequest;
import com.mycom.myapp.user.dto.ResponseRegisterDto;
import com.mycom.myapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<ResponseRegisterDto> register(@Valid @RequestBody RegisterRequest user) {
    ResponseRegisterDto result = userService.registerUser(user);
    return ResponseEntity.ok(result);
  }
}
