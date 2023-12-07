package com.hwamok.auth.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.security.JwtService;
import com.hwamok.security.JwtType;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import com.hwamok.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public Pair<String, String> login(String loginId, String password) {
    User user = userRepository
            .findByLoginIdAndStatus(loginId, UserStatus.ACTIVATED)
            .orElseThrow(() -> new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST));
    //
    // Bcrypt Encoding -> 단방향 암호화
    // Spring Security에서 => PasswordEncoder라는 인터페이스에서 Bcrypt를 사용하길 추천함
    // Bcrypt 생성할 때 평문을 알고리즘으로 5번 돌림 --> 암호화된 값(해시)
    // password(평문)을 5번 돌렸을 때 암호화가 진행된 패스워드의 해시값과 같은지
    // 1234 --> QWEQWEQWE
    // 로그인할때 QWEQWEQWE
    if(!passwordEncoder.matches(password, user.getPassword())) {
      throw new HwamokException(ExceptionCode.FAIL_LOGIN_REQUEST);
    }

    // 여기 코드가 실행된다는건 로그인이 완료
    // User를 리턴해서 controller에서 session에 담아줌 --> 세션 이제 안씀
    // Jwt토큰을 발행함으로써 유저의 로그인 완료를 처리

    // 프론트는 jwt토큰을 가지고 있다가 모든 요청보낼때 jwt토큰을 header에 담아서 보냄

    // 서버에서는 header에 있는 jwt토큰을 해독해서 우리 서버의 토큰이 맞으면 인증된 사용자로 처리
    String accessToken = jwtService.issue(user.getId(), JwtType.ACCESS);
    String refreshToken = jwtService.issue(user.getId(), JwtType.REFRESH);

    // Pair
    return Pair.of(accessToken, refreshToken);
  }
}
