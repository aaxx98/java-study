package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dao.LoginDao;
import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.common.exception.UnauthorizedException;
import com.mycom.myapp.user.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final LoginDao loginDao;

  public LoginService(LoginDao loginDao) {
    this.loginDao = loginDao;
  }

  public UserDto getUserBySessionId(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      throw new UnauthorizedException("로그인 필요");
    }

    Object obj = session.getAttribute("userDto");
    if (!(obj instanceof UserDto userDto)) {
      throw new UnauthorizedException("로그인 필요");
    }

    return userDto;
  }

  public UserDto login(LoginRequestDto loginInfo) {
    UserDto userInfo = loginDao.findUserByEmail(loginInfo.getEmail());
    if (userInfo == null) {
      throw new NotFoundException("가입되지 않은 사용자입니다.");
    }
    if (!loginInfo.getPassword().equals(userInfo.getPassword())) {
      throw new UnauthorizedException("로그인 정보가 일치하지 않습니다.");
    }
    return userInfo;
  }
}
