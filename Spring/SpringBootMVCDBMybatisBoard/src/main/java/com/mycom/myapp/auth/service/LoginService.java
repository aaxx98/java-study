package com.mycom.myapp.auth.service;

import java.util.Optional;

import com.mycom.myapp.user.dto.UserDto;

// Login B.L 처리하는 경우
// 로그인 성공 또는 실패 모두 가능 <= 사용자 실수로 인한 실패 <= 시스템 오류 X <= 대응 코드는 필요
// 호출하는 Controller 에거 null 가능성에 대한 인지 <= Optional
// 호출하는 Controller 에서 구체적인 Optional 대응 코드를 작성
// UserResultDto 처럼 AuthResultDto 를 만들고 처리할 수 있으나, 
//   Optional<UserDto> 필드가 추가되어야 하고, Controller 에서 그걸 꺼내어서 판단해야 하는 부분이 복잡하므로 단순하게 처리하는 코드
public interface LoginService {
	Optional<UserDto> login(UserDto userDto);  // user_email, user_password
}
