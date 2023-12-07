package com.hwamok.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  @Test
  void 유저_생성_성공() {
    User user = new User("testId", "1234", "test@gmail.com", "닉네임", "이름", "1993-01-15");

    assertThat(user.getId()).isNull();
    assertThat(user.getLoginId()).isEqualTo("testId");
    assertThat(user.getPassword()).isEqualTo("1234");
    assertThat(user.getEmail()).isEqualTo("test@gmail.com");
    assertThat(user.getNickname()).isEqualTo("닉네임");
    assertThat(user.getName()).isEqualTo("이름");
    assertThat(user.getBirthDay()).isEqualTo("1993-01-15");
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__loginId가_null_혹은_빈값(String loginId) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User(loginId, "1234", "test@gmail.com", "닉네임", "이름", "1993-01-15"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__password가_null_혹은_빈값(String password) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User("loginId", password, "test@gmail.com", "닉네임", "이름", "1993-01-15"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__email이_null_혹은_빈값(String email) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User("loginId", "1234", email, "닉네임", "이름", "1993-01-15"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__nickname이_null_혹은_빈값(String nickname) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User("loginId", "1234", "test@gmail.com", nickname, "이름", "1993-01-15"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__name이_null_혹은_빈값(String name) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User("loginId", "1234", "test@gmail.com", "닉네임", name, "1993-01-15"));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void 유저_생성_실패__birthDay가_null_혹은_빈값(String birthDay) {
    assertThatIllegalArgumentException().isThrownBy(() -> new User("loginId", "1234", "test@gmail.com", "닉네임",  "이름", birthDay));
  }
}