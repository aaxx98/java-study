package com.junit.test.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.test.domain.User;
import com.junit.test.dto.UserCreateRequest;
import com.junit.test.dto.UserResponse;
import com.junit.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestConfig.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;

  @TestConfiguration
  static class TestConfig {

    @Bean
    UserService userService() {
      return Mockito.mock(UserService.class);
    }
  }

  @Test
  void 회원가입_Controller_테스트() throws Exception {
    // given
    UserCreateRequest request = new UserCreateRequest("test@test.com", "홍길동");

    given(userService.createUser(Mockito.any()))
        .willReturn(getMockUserResponse(1L, "test@test.com", "홍길동"));

    // when & then
    mockMvc.perform(post("/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("test@test.com"))
        .andExpect(jsonPath("$.name").value("홍길동"));
  }

  // Mock Response DTO
  static UserResponse getMockUserResponse(Long id, String email, String name) {
    User user = new User(email, name);
    ReflectionTestUtils.setField(user, "id", id);
    UserResponse response = new UserResponse(user);
    return response;
  }
}
