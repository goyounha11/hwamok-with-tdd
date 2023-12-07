package com.hwamok.user.service;

import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  void 유저_가입_성공() {
    User user = userService.create("testId", "1234", "닉네임", "test@test.com", "이름", "1993-01-15");

    assertThat(user.getId()).isNotNull();
    assertThat(user.getLoginId()).isEqualTo("testId");
    assertThat(user.getPassword()).isNotEqualTo("1234");
    assertThat(user.getNickname()).isEqualTo("닉네임");
    assertThat(user.getEmail()).isEqualTo("test@test.com");
    assertThat(user.getName()).isEqualTo("이름");
    assertThat(user.getBirthDay()).isEqualTo("1993-01-15");
  }

  @Test
  void 유저_단건_조회() {
    User user = userRepository.save(new User("loginId", "1234", "test@gmail.com", "nickname", "name", "1993-01-15"));

    User foundUser = userService.getUser(user.getId());

    assertThat(foundUser.getId()).isEqualTo(user.getId());
  }

  @Test
  void 유저_수정_성공() {
    User user = userRepository.save(new User("loginId", "1234", "test@gmail.com", "nickname", "name", "1993-01-15"));

    user = userService.changeUser(user.getId(), "12", "test@naver.com", "닉넴이에요", "name", "1993-01-15");

    assertThat(user.getPassword()).isEqualTo("12");
    assertThat(user.getEmail()).isEqualTo("test@naver.com");
    assertThat(user.getNickname()).isEqualTo("닉넴이에요");
    assertThat(user.getName()).isEqualTo("name");
    assertThat(user.getBirthDay()).isEqualTo("1993-01-15");
  }

  @Test
  void 유저_삭제() {
    User user = userRepository.save(new User("loginId", "1234", "test@gmail.com", "nickname", "name", "1993-01-15"));

    user = userService.delete(user.getId());

    assertThat(user.getStatus()).isEqualTo(UserStatus.INACTIVATED);
  }
}