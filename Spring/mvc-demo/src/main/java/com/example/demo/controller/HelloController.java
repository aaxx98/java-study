package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
  // @ResponseBody를 사용하지 않으므로 return 하는 "hello"에 대한 jsp 페이지 반환 -> hello.jsp
  @GetMapping("/hello")
  public String hello(){
    System.out.println("/hello 요청");
    return "hello"; // hello.jsp 페이지를 내려줌
  }

  @GetMapping("/hello-world")
  public String helloWorld(){
    System.out.println("/hello-world 요청");
    return "hello"; // hello.jsp 페이지를 내려줌
  }

  @GetMapping("/myhome")
  public void myhome(){ // void인 경우 url 경로 따라 myhome.jsp 내려줌
    System.out.println("/myhome 요청");
  }
}
