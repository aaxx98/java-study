package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.auth.dto.LoginRequest;
import com.mycom.myapp.user.dto.UserDto;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final LoginDao loginDao;

  public LoginService(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public Optional<UserDto> login(LoginRequest loginInfo) {

    UserDto userInfo = loginDao.findUserByEmail(loginInfo.getEmail());

    if (userInfo != null && loginInfo.getPassword().equals(userInfo.getPassword())) {
      userInfo.setPassword(null); // 비밀번호 안정화
      return Optional.of(userInfo);
    }
    return Optional.empty();
  }
}
