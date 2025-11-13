package com.mycom.myapp.user.service;

import org.springframework.stereotype.Service;

import com.mycom.myapp.user.dao.UserDao;
import com.mycom.myapp.user.dto.UserDto;
import com.mycom.myapp.user.dto.UserResultDto;

@Service
public class UserServiceImpl implements UserService{

	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public UserResultDto registerUser(UserDto userDto) {
		UserResultDto userResultDto = new UserResultDto();
		if( userDao.registerUser(userDto) == 1 ) {
			userResultDto.setResult("success");
		}else {
			userResultDto.setResult("fail");
		}
		return userResultDto;
	}

}
