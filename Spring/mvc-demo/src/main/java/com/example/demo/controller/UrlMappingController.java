package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UrlMappingController {

  // url 경로와 메소드를 설정
  @RequestMapping(value = "/m1", method = RequestMethod.GET)
  @ResponseBody
  public void m1() {
    System.out.println("/m1");
  }


  // RequestMapping의 축약형: GetMapping, PostMapping, PutMapping, DeleteMapping
  @GetMapping("/get")
  @ResponseBody
  public void m2() {
    System.out.println("m2");
  }

  @PostMapping("/post")
  @ResponseBody
  public void m3() {
    System.out.println("m3");
  }

  @PutMapping("/put")
  @ResponseBody
  public void m4() {
    System.out.println("m4");
  }

  @DeleteMapping("/delete")
  @ResponseBody
  public void m5() {
    System.out.println("m5");
  }

  @GetMapping("/books/{bookId}")
  @ResponseBody
  public void m6(@PathVariable("bookId") int bookId) {
    System.out.println(bookId);
  }

  @GetMapping("/books/{limit}/{offset}")
  @ResponseBody
  public void m7(@PathVariable("limit") int limit, @PathVariable("offset") int offset) {
    System.out.println(limit);
    System.out.println(offset);
  }

  // 두 url 모두 같은 처리
  @GetMapping(value = {"/url1", "/url2"})
  @ResponseBody
  public void m8() {
    System.out.println("/url1, /url2");
  }

  // /sub1/abc, /sub1/123 등 sub1뒤의 다음 path 자유형식
  @PostMapping("/sub-next/*")
  @ResponseBody
  public void m9() {
    System.out.println("/sub-next");
  }

  @PostMapping("/sub-all/**")
  @ResponseBody
  public void m10() {
    System.out.println("/sub-all");
  }
}