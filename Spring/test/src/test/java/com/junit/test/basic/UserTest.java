package com.junit.test.basic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.junit.test.controller.UserController;
import com.junit.test.domain.User;
import com.junit.test.dto.UserResponse;
import com.junit.test.repository.UserRepository;
import com.junit.test.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

  // #1. repository 테스트
  @Autowired
  private UserRepository userRepository;

  @Test
  public void testLoginRepository() {
    userRepository.save(new User("user3@mycom.com", "홍길동", "password"));

    Optional<User> optionalUser = userRepository.findByEmail("user3@mycom.com");
    assertTrue(optionalUser.isPresent());
  }

  // #2. service 테스트
  @Autowired
  private UserService userService;

  @Test
  public void testService_사용자_조회_성공() {
    userRepository.save(new User("user3@mycom.com", "홍길동", "password"));

    UserResponse userResult = userService.getUser(1L);
    assertNotNull(userResult);
    assertEquals("user3@mycom.com", userResult.getEmail());
  }

  @Test
  public void testService_사용자_조회_실패() {
    assertThrows(IllegalArgumentException.class, () -> {
      userService.getUser(999L);
    });
  }

  // #3. controller 테스트
  @Autowired
  private UserController userController;

  @Autowired
  private HttpSession session;

  @Test
  void login_success() {
    // given
    userRepository.save(new User("user3@mycom.com", "홍길동", "password3"));

    // when
    UserResponse response = userController.login("user3@mycom.com", "password3", session);

    // then
    assertNotNull(response);
    assertEquals("user3@mycom.com", response.getEmail());
    assertNotNull(session.getAttribute("user"));
  }

  @Test
  void login_fail_wrong_password() {
    // given
    userRepository.save(new User("user3@mycom.com", "홍길동", "password3"));

    // when & then
    assertThrows(IllegalArgumentException.class, () ->
        userController.login("user3@mycom.com", "wrongPass", session)
    );
  }
}
