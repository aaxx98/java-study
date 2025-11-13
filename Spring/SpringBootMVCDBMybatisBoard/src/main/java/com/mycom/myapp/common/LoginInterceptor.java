package com.mycom.myapp.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// WebMvcConfig 를 통해 적용 정책과 함께 등록
// 페이지 요청과 데이터 요청을 구분 필요.
//  로그인이 필요한 게시판에서 로그인 후, 세션 타임아웃이 발생하면 브라우저는 그것을 알지 못한다. 데이터 요청을 하면 login 페이지로 이동을 할 수 있어야 한다.
//  Interceptor 가 login 페이지로 이동해야 함을 응답해 줘야 한다.
//  그러기 위해서는 Front 와 Interceptor 사이의 관련 규칙(약속)이 필요.
//  관련 약속은 Front 는 data 요청일 때, Http Header 에 ajax=true 를 항상 보낸다.
@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	private final String jsonStr = "{\"result\":\"login\"}";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		System.out.println("LoginInterceptor >> " + request.getRequestURI());
		
		// session 에 login 항목이 success 인지 확인
		HttpSession session = request.getSession();
		UserDto userDto  = (UserDto) session.getAttribute("userDto");
		
		if( userDto == null ) {
			// 거절
			if( "true".equals( request.getHeader("ajax") )) { // #1. data 요청
				System.out.println("LoginInterceptor >> ajax request 거절");
				response.getWriter().write(jsonStr);
			}else { // #2. 페이지 요청
				System.out.println("LoginInterceptor >> page request 거절");
				response.sendRedirect("/pages/login");
			}

			return false;
		}
		
		// 통과
		return true;
	}
}
