package com.mycom.myapp.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

// JWT + Spring Security로 전환하면 LoginInterceptor는 더이상 필요하지 않음
// JwtAuthenticationFilter에서 토큰 검사 실시
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession(false); // 세션 가져오기, 없으면 null
    if (session != null && session.getAttribute("userDto") != null) {
      return true; // 로그인 되어 있음
    }
    // 로그인 안 되어 있으면 401 에러 반환
    response.setContentType("application/json"); // JSON 형식
    response.setCharacterEncoding("UTF-8");       // 한글 깨짐 방지
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

    // JSON 문자열 작성
    String json = "{\"status\":401, \"message\":\"로그인이 필요합니다.\"}";
    response.getWriter().write(json);
    response.getWriter().flush();
    return false; // 컨트롤러 실행 중단
  }
}