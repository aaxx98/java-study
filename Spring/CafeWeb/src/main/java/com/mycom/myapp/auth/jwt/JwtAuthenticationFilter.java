package com.mycom.myapp.auth.jwt;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// OncePerRequestFilter: 모든 request에 대해 실행되는 필터
// Jwt 토큰을 검증함
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider provider;
  private final LoginDao loginDao;

  public JwtAuthenticationFilter(JwtTokenProvider provider, LoginDao loginDao) {
    this.provider = provider;
    this.loginDao = loginDao;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {

    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);

      provider.validate(token);

      String email = provider.getUserEmail(token);
      UserDto user = loginDao.findUserByEmail(email);

      // 현재 들어온 요청을 처리하는 동안에만 인증 정보를 임시로 저장(스프링 시큐리티), 요청 처리에 필요시 사용함
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(user, null, null);

      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(request, response);
  }
}
