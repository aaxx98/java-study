package com.mycom.myapp.auth.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.user.dto.UserDto;

@Mapper
public interface LoginDao {
	// 사용자는 userEmail, userPassword 를 백엔드에 전달
	// dao 는 userEmail 로 사용자를 검색해서 있으면 UserDto 객체를 채운다. 없으면 null 로 return
	UserDto login(String userEmail);
}
