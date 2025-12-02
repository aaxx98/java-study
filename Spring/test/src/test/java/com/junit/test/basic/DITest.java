package com.junit.test.basic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.junit.test.controller.UserController;
import com.junit.test.repository.UserRepository;
import com.junit.test.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

// Spring Web Application 코드 테스트
// src/main/java/com/mycom/myapp 이하 코드들을 테스트
// Controller 가 http request, response 동작하는 환경
// 모든 코드를 테스트할 수 있지만, 느리다.
// console 에서 Spring Boot Web Application 을 위한 내장 톰캣이 동작하고 테스트 코드가 진행 후, 자동 종료 확인

@SpringBootTest  // 모든 Spring Boot Web Application 을 사용 ( 톰캣의 내장엔진 동작 등등 )
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j // Spring Boot 의 기본 설정된 Logger 객체를 자동으로 생성, 테스트 코드 및 기본 개발 코드에서도 사용 가능
public class DITest {
  // DI 테스트

  @Autowired
  UserController userController;

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Test
  @Order(1)
  void testDI() {
    log.info("testDI() 시작");
    assertNotNull(userController);
    assertNotNull(userService);
    assertNotNull(userRepository);
    log.info("testDI() 종료");
  }

  @Test
  @Order(2)
    // logging level 을 debug
  void testDIAll() {
    log.debug("testDIAll() 시작");
    assertAll("DI 묶음 테스트",
        () -> assertNotNull(userController),
        () -> assertNotNull(userService),
        () -> assertNotNull(userRepository)
    );
    log.debug("testDIAll() 종료");
  }

  @Autowired
  HttpSession session;

  @Autowired
  HttpServletRequest request;

  @Test
  @Order(3)
    // logging level 을 debug
  void testDISessionRequest() {
    log.info("testDISessionRequest() 시작");
    assertAll("DI 묶음 테스트",
        () -> assertNotNull(session),
        () -> assertNotNull(request)
    );
    log.info("testDISessionRequest() 종료");
  }


}