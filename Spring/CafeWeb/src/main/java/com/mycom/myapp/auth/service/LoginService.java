package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final LoginDao loginDao;

  public LoginService(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public Optional<UserDto> getUserBySessionId(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      Object obj = session.getAttribute("userDto");
      if (obj != null && obj instanceof UserDto userDto) {
        return Optional.of(userDto);
      }
    }
    return Optional.empty();
  }

  public Optional<UserDto> login(LoginRequestDto loginInfo) {

    UserDto userInfo = loginDao.findUserByEmail(loginInfo.getEmail());

    if (userInfo != null && loginInfo.getPassword().equals(userInfo.getPassword())) {
      userInfo.setPassword(null); // 비밀번호 안정화
      return Optional.of(userInfo);
    }
    return Optional.empty();
  }
}
