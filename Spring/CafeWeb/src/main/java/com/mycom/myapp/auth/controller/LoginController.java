package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/auth")
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> getUserBySessionId(HttpServletRequest request) {
    UserDto user = loginService.getUserBySessionId(request);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest,
      HttpServletRequest request) {
    UserDto user = loginService.login(loginRequest);
    HttpSession session = request.getSession(true); // 없으면 새로 생성
    session.setAttribute("userDto", user);
    return ResponseEntity.ok(Map.of("message", "로그인 성공"));
  }
}
