package com.junit.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.junit.test.dto.UserCreateRequest;
import com.junit.test.dto.UserResponse;
import com.junit.test.repository.UserRepository;
import com.junit.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserIntegrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  void 회원가입_전체흐름_성공() {
    // given
    UserCreateRequest request = new UserCreateRequest("hello@test.com", "홍길동");

    // when
    UserResponse response = userService.createUser(request);

    // then
    assertNotNull(response.getId());
    assertTrue(userRepository.existsByEmail("hello@test.com"));
  }

  @Test
  void 중복이메일_회원가입_실패() {
    // first create
    userService.createUser(new UserCreateRequest("dup@test.com", "철수"));

    // then
    assertThrows(IllegalArgumentException.class,
        () -> userService.createUser(new UserCreateRequest("dup@test.com", "영희"))
    );
  }
}
