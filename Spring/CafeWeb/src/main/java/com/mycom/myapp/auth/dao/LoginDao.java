package com.mycom.myapp.auth.dao;

import com.mycom.myapp.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {

  UserDto findUserByEmail(String email);
}
