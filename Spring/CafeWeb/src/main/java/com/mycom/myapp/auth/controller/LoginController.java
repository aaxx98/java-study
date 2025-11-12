package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<?> getUserBySessionId(HttpServletRequest request) {
    Optional<UserDto> user = loginService.getUserBySessionId(request);
    if (user.isPresent()) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "로그인 한 후 사용 가능합니다."));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest,
      HttpServletRequest request) {
    Optional<UserDto> user = loginService.login(loginRequest);
    if (user.isPresent()) {
      HttpSession session = request.getSession(true); // 없으면 새로 생성
      session.setAttribute("userDto", user.get());
      return ResponseEntity.ok(Map.of("message", "로그인 성공"));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "아이디 또는 비밀번호 오류"));
  }
}
