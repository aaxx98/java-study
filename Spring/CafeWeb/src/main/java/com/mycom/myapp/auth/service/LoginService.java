package com.mycom.myapp.auth.service;

import com.mycom.myapp.auth.dto.LoginRequestDto;
import com.mycom.myapp.auth.dto.LoginResponseDto;
import com.mycom.myapp.auth.jwt.JwtTokenProvider;
import com.mycom.myapp.common.exception.NotFoundException;
import com.mycom.myapp.common.exception.UnauthorizedException;
import com.mycom.myapp.user.dao.UserDao;
import com.mycom.myapp.user.dto.ResponseRegisterDto;
import com.mycom.myapp.user.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final UserDao userDao;
  private final PasswordEncoder encoder;
  private final JwtTokenProvider jwtTokenProvider;

  public LoginService(UserDao userDao, PasswordEncoder encoder,
      JwtTokenProvider jwtTokenProvider) {
    this.userDao = userDao;
    this.encoder = encoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public LoginResponseDto login(LoginRequestDto loginInfo) {
    UserDto userInfo = userDao.findUserByEmail(loginInfo.getEmail());
    if (userInfo == null) {
      throw new NotFoundException("가입되지 않은 사용자입니다.");
    }
    if (!encoder.matches(loginInfo.getPassword(), userInfo.getPassword())) {
      throw new UnauthorizedException("로그인 정보가 일치하지 않습니다.");
    }
    String token = jwtTokenProvider.createToken(userInfo.getId(), userInfo.getEmail(),
        userInfo.getName());
    LoginResponseDto loginSuccess = new LoginResponseDto(token);

    ResponseRegisterDto responseUser = new ResponseRegisterDto();
    responseUser.setStatus("Login Success");
    responseUser.setName(userInfo.getName());
    responseUser.setEmail(userInfo.getEmail());
    loginSuccess.setUser(responseUser);
    return loginSuccess;
  }
}
