package com.mycom.myapp.user.service;

import com.mycom.myapp.user.dao.UserDao;
import com.mycom.myapp.user.dto.ResponseRegisterDto;
import com.mycom.myapp.user.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserDao userDao;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  public ResponseRegisterDto registerUser(UserDto user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword()); // 비밀번호 암호화
    user.setPassword(encodedPassword);

    int inserted = userDao.registerUser(user);
    if (inserted <= 0) {
      throw new IllegalStateException("회원 등록에 실패했습니다.");
    }
    ResponseRegisterDto response = new ResponseRegisterDto();
    response.setName(user.getName());
    response.setEmail(user.getEmail());
    response.setStatus("Register Success");
    return response;
  }
}
