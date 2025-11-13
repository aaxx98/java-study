package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

// Service Layer 에서 표준 응답 객체를 리턴
public interface UserService {
	// 회원 가입
	UserResultDto registerUser(UserDto userDto);
}
