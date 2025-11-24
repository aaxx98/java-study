package com.mycom.myapp.auth.controller;

import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.auth.dto.LoginResponseDto;
import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.ResponseRegisterDto;
import com.mycom.myapp.user.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  public ResponseEntity<UserDto> getUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserDto user = (UserDto) auth.getPrincipal();
    return ResponseEntity.ok(user);
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseRegisterDto> login(@RequestBody LoginRequestDto loginRequest) {
    LoginResponseDto loginSuccess = loginService.login(loginRequest);

    // HttpOnly 쿠키 생성
    ResponseCookie cookie = ResponseCookie.from("accessToken", loginSuccess.getAccessToken())
        .httpOnly(true)
        .secure(false) // http: false, https: true
        .path("/")
        .sameSite("Lax")
        .maxAge(30 * 60) // 30분
        .build();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(loginSuccess.getUser());
  }
}
