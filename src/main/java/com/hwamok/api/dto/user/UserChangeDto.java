package com.hwamok.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserChangeDto {
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {
    private String password;
    private String email;
    private String nickname;
    private String name;
    private String birthDay;
  }
}
