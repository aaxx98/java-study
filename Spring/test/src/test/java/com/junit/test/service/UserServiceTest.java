package com.junit.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.junit.test.domain.User;
import com.junit.test.dto.UserCreateRequest;
import com.junit.test.dto.UserResponse;
import com.junit.test.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceTest {

  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final UserService userService = new UserService(userRepository);

  @Test
  void 중복이메일이면_회원가입_실패() {
    // given
    given(userRepository.existsByEmail("test@test.com")).willReturn(true);

    // when & then
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> userService.createUser(new UserCreateRequest("test@test.com", "홍길동"))
    );
    assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
  }

  @Test
  void 새로운이메일이면_회원가입_성공() {
    // given
    given(userRepository.existsByEmail("new@test.com")).willReturn(false);

    // 가짜 User 반환 설정
    User mockUser = new User("new@test.com", "홍길동");
    given(userRepository.save(any(User.class))).willReturn(mockUser);

    // when
    UserResponse response = userService.createUser(
        new UserCreateRequest("new@test.com", "홍길동")
    );

    // then
    assertEquals("new@test.com", response.getEmail());
    assertEquals("홍길동", response.getName());
  }
}
