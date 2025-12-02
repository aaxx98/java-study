package com.junit.test.controller;

import com.junit.test.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

  @GetMapping("/hello")
  public String m1() {
    log.info("/hello");
    return "hello";
  }

  @GetMapping("/param1")
  public String m2(@RequestParam("id") Integer id,
      @RequestParam("name") String name) {
    log.info("/param1");
    log.info("id : {} name : {}", id, name);
    return "ok";
  }

  @PostMapping("/param2")
  public String m3(@RequestBody TestDto testDto) {
    log.info("/param2");
    log.info(testDto.toString());
    return "ok";
  }
}
