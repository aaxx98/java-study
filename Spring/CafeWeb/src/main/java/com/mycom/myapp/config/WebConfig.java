package com.mycom.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  // 로그인 확인 로직 -> Spring Security 설정으로 옮김
//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(loginInterceptor)
//        .addPathPatterns("/**")
//        .excludePathPatterns(
//            "/api/auth/login",
//            "/api/auth/me",
//            "/api/user/register");
//  }

  // vite의 프록시 설정을 사용하므로 개발환경에서 불필요하여 주석처리
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")          // 모든 API 경로
//        .allowedOrigins("http://localhost:5173")  // 허용할 프론트 URL
//        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용 HTTP 메서드
//        .allowCredentials(true);
//  }
}
