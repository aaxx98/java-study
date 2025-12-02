package com.junit.test.basic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // @Order annotation 사용 가능
public class OrderTest {

  @Test // 아래 메소드를 Junit 으로 테스트
  @Order(2)
  void test1() {
    System.out.println("test1()");
  }

  @Test
  @Order(1)
  void test2() {
    System.out.println("test2()");
  }

  @Test
  @DisplayName("회원 등록 테스트")
//	@Order(3)
  @Order(4)
  void test3() {
    System.out.println("test3()");
  }

  @Test
  @DisplayName("회원 수정 테스트")
//	@Order(4)
  @Order(3)
    // 3번째 실행되면서 NullPointerException 이 발생 -> 회원 수정 테스트는 실패 -> 4번 째 회원 등록 테스트 는 계속 이어 간다.
  void test4() {
    String s = null;
    s.length();
    System.out.println("test4()");
  }

  // @BeforeAll, @AfterAll <- static method, junit dashboard 는 표시 X
  @BeforeAll // 테스트를 위한 schema 및 테이블 초기화, 초기 데이터 적재
  static void beforeAll() {
    System.out.println("beforeAll()");
  }

  @AfterAll // 테스트를 위한 schema 및 테이블 초기화, 초기 데이터 적재 정지 작업
  static void afterAll() {
    System.out.println("afterAll()");
  }

  // @BeforeEach, @AfterEach <- junit dashboard 는 표시 X
  @BeforeEach
  // 모든 개별 테스트 메소드 호출 전에 실행
  void beforeEach() {
    System.out.println("beforeEach()");
  }

  @AfterEach
    // 모든 개별 테스트 메소드 호출 후에 실행 - 개별 테스트 메소드가 RuntimeException 이 발생해도 실행된다.
  void AfterEach() {
    System.out.println("AfterEach()");
  }
}
