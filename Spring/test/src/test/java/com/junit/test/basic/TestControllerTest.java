package com.junit.test.basic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.test.controller.TestController;
import com.junit.test.dto.TestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// Controller 계층만 로딩, 실제 컨트롤러 메서드를 호출함
@WebMvcTest(TestController.class)
class TestControllerTest {

  // @SpringBootTest와는 다르게 톰캣 띄우지 않음
  // Mock HTTP 요청 보냄
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void hello_test() throws Exception {
    mockMvc.perform(get("/hello"))
        .andExpect(status().isOk())
        .andExpect(content().string("hello"));
  }

  @Test
  void param1_test() throws Exception {
    mockMvc.perform(get("/param1")
            .param("id", "10")
            .param("name", "홍길동"))
        .andExpect(status().isOk())
        .andExpect(content().string("ok"));
  }

  @Test
  void param2_test() throws Exception {
    TestDto dto = new TestDto();
    dto.setId(10);
    dto.setName("홍길동");

    mockMvc.perform(post("/param2")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().string("ok"));
  }
}