package com.hwamok.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Slf4j
@Component
public class StopWatchAop {
  // Aspect Oriented Programming
  // Aspect --> 프레임워크
  // Spring에서 자기네들 입맛대로 조금 개조

  // execute 구현 --> 이름이 관례
  // execute가 동작할 범위를 지정 pakage단위부터 함수 1개단위까지 지정이 가능
  @Around("execution(* com.hwamok..*(..))")
  public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    log.info("Start Stopwatch : {}", joinPoint.toString());
    try {
      return joinPoint.proceed();
    } finally {
      stopWatch.stop();
      log.info("End Stopwatch : {} During Time : {}", joinPoint.toString(), stopWatch.getTotalTimeSeconds());
    }
  }
}
