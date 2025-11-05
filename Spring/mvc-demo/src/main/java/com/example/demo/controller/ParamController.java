package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ParamController {
  @GetMapping("/low-level-code")
  @ResponseBody
  public String m1(HttpServletRequest request) {
    // 쿼리 파라미터를 request로 부터 읽음
    // http://localhost:8080/low-level-code?bookId=1&bookName=MyBook
    System.out.println(request.getParameter("bookId"));
    System.out.println(request.getParameter("bookName"));
    return "OK";
  }

  @GetMapping("/auto-mapping")
  @ResponseBody
  public String m2(String bookId, String bookName) {
    // 쿼리 파라미터가 메소드 파라미터에 자동 매핑
    // http://localhost:8080/auto-mapping?bookId=1&bookName=MyBook
    System.out.println(bookId);
    System.out.println(bookName);
    return "OK";
  }

  @GetMapping("/auto-diff")
  @ResponseBody
  public String m3(@RequestParam("bookId") String id, @RequestParam("bookName") String name) {
    // 쿼리 파라미터명과 메소드 파라미터명이 다른 경우의 매핑
    // http://localhost:8080/auto-diff?bookId=1&bookName=MyBook
    System.out.println(id);
    System.out.println(name);
    return "OK";
  }

  @GetMapping("/nullable")
  @ResponseBody
  public BookDto m4(@RequestParam(name = "bookId", required = false) String id, @RequestParam(name = "bookName", required = false) String name) {
    // 파라미터값이 required아닌 경우 required false 설정
    // http://localhost:8080/nullable
    // http://localhost:8080/nullable?bookId=1&bookName=MyBook (파라미터 입력 선택 가능)
    System.out.println(id);
    System.out.println(name);
    return new BookDto(id, name); // json으로 자동 직렬화(@ResponseBody)
  }

  @PostMapping("/use-dto")
  @ResponseBody
  public BookDto useDto(BookDto book) { // request body를 Dto 클래스 형식으로 자동 변환하여 받음
    // http://localhost:8080/use-dto (POST)
    return book; // json으로 자동 변환하여 반환(@ResponseBody)
  }
}

