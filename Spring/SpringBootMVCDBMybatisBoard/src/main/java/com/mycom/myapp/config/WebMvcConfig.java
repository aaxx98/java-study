package com.mycom.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mycom.myapp.common.LoginInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 전체 url 적용, 예외 추가
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(
					"/", "/index.html",
					"/assets/**",
					"/pages/login",
					"/pages/register",
					"/auth/login"
				);	
	}
}
