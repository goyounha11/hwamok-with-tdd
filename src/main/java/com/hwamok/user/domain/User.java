package com.hwamok.user.domain;

import com.hwamok.core.Preconditions;
import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.util.RegexType;
import com.hwamok.util.RegexUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;

import static com.hwamok.core.Preconditions.*;
import static com.hwamok.core.exception.ExceptionCode.ERROR_SYSTEM;
import static com.hwamok.core.exception.HwamokException.validate;
import static com.hwamok.util.RegexType.PASSWORD;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String loginId;
  private String password;
  private String email;
  private String nickname;
  private String name;
  private UserStatus status = UserStatus.ACTIVATED;
  private String birthDay;
  private Instant createdAt = Instant.now();

  public User(String loginId, String password, String email, String nickname, String name, String birthDay) {
    require(Strings.isNotBlank(loginId));
    require(Strings.isNotBlank(password));
    require(Strings.isNotBlank(email));
    require(Strings.isNotBlank(nickname));
    require(Strings.isNotBlank(name));
    require(Strings.isNotBlank(birthDay));

    this.loginId = loginId;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.name = name;
    this.birthDay = birthDay;
  }

  public void change(String password, String email, String nickname, String name, String birthDay) {
    require(Strings.isNotBlank(password));
    require(Strings.isNotBlank(email));
    require(Strings.isNotBlank(nickname));
    require(Strings.isNotBlank(name));
    require(Strings.isNotBlank(birthDay));

    this.password = password;
    this.email = email;
    this.nickname = nickname;
    this.name = name;
    this.birthDay = birthDay;
  }

  public User inactivated() {
    this.status = UserStatus.INACTIVATED;

    return this;
  }
}
