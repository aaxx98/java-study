package com.mycom.myapp.user.dao;

import com.mycom.myapp.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

  void registerUser(UserDto user);
}
