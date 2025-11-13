package com.mycom.myapp.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

// page (html) 이동을 담당.
@Controller
public class PageController {

	@GetMapping("/pages/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/pages/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/pages/board")
	public String board() {
		return "board";
	}
	
	// logout
	@GetMapping("/pages/logout")
	public String logout(HttpSession session) {
		// session 정리
		session.invalidate();
		// 마지막 logout 시각을 관리? <= LoginCongroller 에서 처리하는 게 바람직하다.
		return "login";
	}
}
