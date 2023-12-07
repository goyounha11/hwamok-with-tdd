package com.hwamok.core.exception;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j // 로깅
@RestControllerAdvice
public class ApiExceptionHandler {
  // 여기에 어떤 익셉션 하고 정의해두면
  // 익셉션이 터지면 Advice에서 캐치

  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ApiResult<?>> exception(Throwable throwable) {
    log.info(throwable.getMessage());
    return Result.error();
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<ApiResult<?>> illegalArgumentException(IllegalArgumentException e) {
    log.info(e.getMessage());

    return Result.error(ExceptionCode.REQUIRED_PARAMETER);
  }

  @ExceptionHandler({HwamokException.class})
  public ResponseEntity<ApiResult<?>> hwamokException(HwamokException e) {
    log.info(e.getMessage());

    return Result.error(e.getExceptionCode());
  }
}
