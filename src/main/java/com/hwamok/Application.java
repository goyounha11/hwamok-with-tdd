package com.hwamok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
  // Spring MVC패턴에 맞춰서 개발 <-- 상당히 어려운 개발 방법

  // 1.Controller  의존성이 제일 크고 --> 기능 중심의 개발
  // 2.AService
  // 3.ARepository
  // 4.Entity       의존성이 제일 없음 --> 비지니스 모델 중심 개발 --> 도메인 중심 개발

  // 뭘 리턴할지 모르니까 일단 void
  // 뭐 필요한지 모르니까 일단 비워두고

  // 클린아키텍쳐 살짝 바꾼 방식
  // notice - domain
  //        - service

  // api - controller

  // Entity
  // Repository
  // Service
  // Controller

  // 테스트 코드란 무엇일까?
  // 코드가 잘 돌아가나 확인해보는 것
  // 실행의 결과가 원하는 결과인지 보는 것
  // 인풋 값에 따라 에러가 날 수 있는 상황들을 확인

  // 내가 만든 코드가 나에게 신뢰성을 주는 것
  // 테스트 코드의 목적은 실패할 만한 상황을 계속 생각하면서
  // 코드를 견고하게 작성하는 것에 목적이 있음

  // A메소드

  // 테스트 코드 작성 -> 실패 하는 테스트
  // 어플리케이션 코드를 수정 -> 실패하지 않도록
  // 테스트를 다시 돌려서 테스트가 성공

  // TDD Test Driven Development
  // 1. 최소한의 성공하는 어플리케이션 코드를 작성한다.
  // 2. 실패하는 테스트 케이스를 작성한다.
  // 3. 실패하지 않도록 어플리케이션 코드를 수정한다.
  // 4. 실패하던 테스트케이스가 성공한다.
  // 5. 테스트 코드의 중복을 최소화한다.
  // 6. 테스트 코드와 어플리케이션 코드를 리팩터링한다.
  // 7. 반복한다.

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
