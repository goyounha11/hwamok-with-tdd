package com.hwamok.core.response;

import com.hwamok.core.exception.ExceptionCode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiResult<T> {
  // code : xxxx
  // message : ~~~
  // data : T
  private String code;
  private String message;
  private T data;

  // 정적팩토리메소드패턴 => 고유의 이름(of)을 하나 부여해주는 패턴

  // 오버로딩 -> 개많이 만드네 똑같은거;;
  // 오버라이딩 -> 재정의하네 함수를?
  public static ApiResult<?> of(ExceptionCode exceptionCode) {
    return ApiResult.of(exceptionCode, null);
  }

  public static <T> ApiResult<T> of(ExceptionCode exceptionCode, T data) {
    return ApiResult.<T>builder()
            .code(exceptionCode.getCode())
            .message(exceptionCode.getMessage())
            .data(data)
            .build();
  }
}
