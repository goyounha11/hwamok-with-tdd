package com.hwamok.core.exception;

import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

@Getter
public class HwamokException extends RuntimeException {
  // HwamokException에 ExceptionCode 를 넣어서 Exception 발생
  private final ApiResult<?> apiResult;
  private final ExceptionCode exceptionCode;

  public HwamokException(ExceptionCode exceptionCode) {
    super(exceptionCode.name());
    this.apiResult = ApiResult.of(exceptionCode);
    this.exceptionCode = exceptionCode;
  }

  public static void validate(boolean expression, ExceptionCode exceptionCode) {
    if(!expression) {
      throw new HwamokException(exceptionCode);
    }
  }
}
