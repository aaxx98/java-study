package com.mycom.myapp.config;

import com.mycom.myapp.common.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  private LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(
            "/api/auth/login",
            "/api/user/register");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")          // 모든 API 경로
        .allowedOrigins("http://localhost:5173")  // 허용할 프론트 URL
        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용 HTTP 메서드
        .allowCredentials(true);
  }
}
