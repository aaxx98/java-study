package com.mycom.myapp.config;

import com.mycom.myapp.auth.jwt.JwtAuthenticationFilter;
import com.mycom.myapp.auth.jwt.JwtTokenProvider;
import com.mycom.myapp.user.dao.UserDao;
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
  private final UserDao userDao;
  private final CustomAuthenticationEntryPoint entryPoint;

  public SecurityConfig(JwtTokenProvider tokenProvider, UserDao userDao,
      CustomAuthenticationEntryPoint entryPoint) {
    this.tokenProvider = tokenProvider;
    this.userDao = userDao;
    this.entryPoint = entryPoint;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(tokenProvider, userDao);

    http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint))
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/login",
                "/api/user/register",
                "/swagger-ui/**",
                "/v3/api-docs/**"
            )
            .permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
