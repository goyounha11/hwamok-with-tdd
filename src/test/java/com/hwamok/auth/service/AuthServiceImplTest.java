package com.hwamok.auth.service;

import com.hwamok.user.domain.UserRepository;
import fixture.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
class AuthServiceImplTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthService authService;

  @Test
  void 로그인_성공() {
    userRepository.save(UserFixture.createUser("testId", passwordEncoder.encode("1234")));

    authService.login("testId", "1234");
  }
}