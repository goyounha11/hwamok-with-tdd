package com.hwamok.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
  // 응답에 필요한 모든 코드와 메시지를 정의
  // code : S000, message : success
  // enum : 열거형 클래스

  SUCCESS("S000", "success"),
  ERROR_SYSTEM("E000", "치명적인 오류가 발생 했습니다."),
  REQUIRED_PARAMETER("E001", "필수 값이 누락 되었습니다."),
  NOT_FOUND_NOTICE("E002", "공지사항을 찾을 수 없습니다."),
  NOT_FOUND_USER("E003", "유저를 찾을 수 없습니다."),
  FAIL_LOGIN_REQUEST("E004", "아이디 또는 비밀번호를 확인해주세요."),
  ACCESS_DENIED("E005", "권한이 없습니다.");
  private final String code; // 재할당 금지
  private final String message;

  ExceptionCode(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
