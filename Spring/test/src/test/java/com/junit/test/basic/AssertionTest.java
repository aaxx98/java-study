package com.junit.test.basic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.core.annotation.Order;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AssertionTest {

  // 예외 발생 테스트
  int getStringLength(String str) {
    return str.length();
  }

  @Test
  @Order(1)
  void test1() {
    String str = null;
    // lamda를 통해 실행할 로직 작성
    assertThrows(NullPointerException.class, () -> getStringLength(str),
        "NullPointerException throws");
  }

  // 묶음 테스트
  int result = 0;

  int m1() {
    return 4;
  }

  //	boolean m2() { return true; };
  boolean m2() {
    return false;
  }

  ;

  String m3() {
    return "hello";
  }

  ;

  @Test
  @Order(2)
  void test2() {
    // 관련성이 있는 테스트를 한꺼번에 묶어서
    // 순서대로 호출해서 정확한 결과가 나오는지 테스트 가능 result 변수 예시
    assertAll("묶음 테스트",
        () -> assertEquals(4, m1()),
        () -> assertFalse(m2()),
        () -> assertNotNull(m3()),
        () -> assertEquals(result, 0)
    );
  }


  @Test
  @Order(3)
  void test3() {
    // Array
    int[] expectedArray = {1, 2, 3};
    int[] actualArray = {1, 2, 3};
//	int[] actualArray = { 1, 2, 3, 4 }; // 길이가 다르면 실패
//  int[] actualArray = {1, 2, 4}; // 길이가 같아도 개별 항목이 다르면 실패

    assertArrayEquals(expectedArray, actualArray, "두 정수 배열이 같다.");
  }


  @Test
  @Order(4)
  void test4() {
    // Collection
    List<String> expectedList = List.of("abc", "def");
    List<String> actualdList = List.of("abc", "def");
//	List<String> actualdList = List.of("abc", "def", "xyz"); // 길이가 다르면 실패
//  List<String> actualdList = List.of("abc", "xyz"); // 길이가 같아도 개별 항목이 다르면 실패

    assertIterableEquals(expectedList, actualdList, "두 문자열 컬렉션이 같다.");
  }

  // 객체 비교
  @Test
  @Order(5)
  void test5() {
    String str1 = "hello";
    String str2 = str1; // str2 는 str1 객체를 참조
    String str3 = new String("hello"); // str1, str3 모두 다른 객체를 참조
    assertSame(str1, str2, "두 객체는 같다.");
    assertNotSame(str1, str3, "두 객체는 참조값이 다르다.");
  }

  @Test
  @Order(6)
  void test6() {
    String str1 = "hello";
    String str2 = new String("hello"); // str1, str2 모두 다른 객체를 참조
    assertEquals(str1, str2, "두 객체는 같다"); // equals() & hashcode()
  }

  void testBL() {
    System.out.println("testBL()");
    // 3초 대기 후 종료
    try {
      Thread.sleep(3000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  @Order(7)
  void test7() {
    // 수행시간 테스트
    assertTimeout(Duration.ofSeconds(1), () -> testBL(), "1초 미만 수행 테스트");
  }
}

