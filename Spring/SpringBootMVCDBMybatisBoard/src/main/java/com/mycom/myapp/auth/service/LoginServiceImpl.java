package com.mycom.myapp.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.user.dto.UserDto;

@Service
public class LoginServiceImpl implements LoginService{

	private final LoginDao loginDao;
	
	public LoginServiceImpl(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	@Override
	public Optional<UserDto> login(UserDto userDto) {
		UserDto dto = loginDao.login(userDto.getUserEmail());
		
		if( dto != null && userDto.getUserPassword().equals(dto.getUserPassword() )) {
			
			dto.setUserPassword(null); // 비밀번호 안정화
			
			return Optional.of(dto); // 성공
		}
		return Optional.empty(); // 실패
	}

}
