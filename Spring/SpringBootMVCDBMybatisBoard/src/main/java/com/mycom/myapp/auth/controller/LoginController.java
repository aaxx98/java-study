package com.mycom.myapp.auth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycom.myapp.auth.service.LoginService;
import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/auth")
public class LoginController {

	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	// 표준 응답을 AuthResultDto (#1) 형식으로 만들어도 되고,
	// Map (#2) 을 이용하는 방법도 있다. ( key - value 쌍 ) 여러 개 응답

	// session.setAttribute("userDto", userDto);
	//  #1. 로그인 여부 확인
	//  #2. 필요에 따라 백엔드에서 사용자 정보를 활용
	@PostMapping("/login")
	public Map<String, String> login(UserDto dto, HttpSession session){
		Map<String, String> map = new HashMap<>();
		Optional<UserDto> optional = loginService.login(dto);
		
		// #1 isPresent() + get()
//		if( optional.isPresent() ) {
//			// session 에 userDto 객체를 저장
//			UserDto userDto = optional.get();
//			session.setAttribute("userDto", userDto);
//			map.put("result", "success");
//			return map;
//		}
//		map.put("result", "fail");
//		return map;
		
		// #2 ifPresentOrElse
		optional.ifPresentOrElse( 
			userDto -> {
				session.setAttribute("userDto", userDto);
				map.put("result", "success");
			}, 
			() -> {
				map.put("result", "fail");
			}
		);
		return map;
	}
}










