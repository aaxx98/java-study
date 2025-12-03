package com.junit.test.service;

import com.junit.test.domain.User;
import com.junit.test.dto.UserCreateRequest;
import com.junit.test.dto.UserResponse;
import com.junit.test.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse createUser(UserCreateRequest request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new IllegalArgumentException("이메일은 비어 있을 수 없습니다.");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
    }

    User user = new User(request.getEmail(), request.getName());
    userRepository.save(user);
    return new UserResponse(user);
  }

  @Transactional(readOnly = true)
  public UserResponse getUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    return new UserResponse(user);
  }

  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new IllegalArgumentException("삭제할 사용자가 존재하지 않습니다.");
    }
    userRepository.deleteById(id);
  }

  public User login(String email, String password) {

    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (!user.getPassword().equals(password)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return user;
  }
}

