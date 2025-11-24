package com.mycom.myapp.config;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.auth.jwt.JwtAuthenticationFilter;
import com.mycom.myapp.auth.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Controller 처리 전
@Configuration
public class SecurityConfig {

  private final JwtTokenProvider tokenProvider;
  private final LoginDao loginDao;
  private final CustomAuthenticationEntryPoint entryPoint;

  public SecurityConfig(JwtTokenProvider tokenProvider, LoginDao loginDao,
      CustomAuthenticationEntryPoint entryPoint) {
    this.tokenProvider = tokenProvider;
    this.loginDao = loginDao;
    this.entryPoint = entryPoint;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, loginDao);

    http
        .csrf(AbstractHttpConfigurer::disable)
//        .cors(cors -> cors.configurationSource(corsConfig()))
        .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/login", "/api/user/register").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // vite의 프록시 설정을 사용하므로 개발환경에서 불필요하여 주석처리
//  @Bean
//  public CorsConfigurationSource corsConfig() {
//    CorsConfiguration config = new CorsConfiguration();
//    config.setAllowedOrigins(List.of("http://localhost:5173"));
//    config.setAllowedMethods(List.of("*"));
//    config.setAllowedHeaders(List.of("*"));
//    config.setAllowCredentials(true);
//
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", config);
//    return source;
//  }
}
