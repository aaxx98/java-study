package com.mycom.myapp.user.dao;

import com.mycom.myapp.common.entity.User;
import com.mycom.myapp.user.dto.UserResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

  UserResponse findUserByEmail(String email);

  int registerUser(User user);
}
