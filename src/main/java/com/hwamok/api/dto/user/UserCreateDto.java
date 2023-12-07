package com.hwamok.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class UserCreateDto {
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {
    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private String name;
    private String birthDay;
  }

}
