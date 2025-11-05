package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String loginForm() {
    return "login/login"; // /login/login.jsp로 이동
  }

  @PostMapping("/login")
  public String login(
      @RequestParam String userName,
      @RequestParam String password,
      HttpSession session) {
    if ("admin".equals(userName) && "1234".equals(password)) {
      session.setAttribute("username", userName); // 세션에 로그인 user 정보 저장
      return "redirect:/home";
    } else {
      return "redirect:/login?error";
    }
  }

  // 로그아웃인데 왜 post로 안하지?
  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/login";
  }

  @GetMapping("/home")
  public String home(HttpSession session) {
    return "login/home"; // /login/home.jsp
  }
}
