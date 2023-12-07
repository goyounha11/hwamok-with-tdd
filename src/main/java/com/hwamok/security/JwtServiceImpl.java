package com.hwamok.security;

import com.hwamok.core.Preconditions;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.hwamok.core.Preconditions.*;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
  private static final long EXPIRE_MINUTES = 30_000; //30초
  private static final long EXPIRE_DAYS = 86_400_000;//24시간

  @Value("${jwt.secretKey}")
  private String secretKey;

  @Override
  public String issue(Long userId, JwtType type) {
    // jwt 토큰은 두가지 종류로 나뉨
    // accessToken - 만료주기가 짧은 토큰 (1분)
    // refreshToken - 만료주기가 긴 토큰
    // claims 정보
    Date now = new Date();
    Date expiration = null;

    switch (type) {
      case ACCESS -> expiration = new Date(now.getTime() + EXPIRE_MINUTES);
      case REFRESH -> expiration = new Date(now.getTime() + EXPIRE_DAYS);
    }


    return Jwts.builder()
            .issuer("hwamok")
            .subject("hwamok jwt api token")
            .issuedAt(now)
            .expiration(expiration)
            .claim("id", userId)
            .signWith(generateSecretKey())
            .compact();
  }

  @Override
  public String resolveToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if(token != null && token.startsWith("Bearer")) {
      return token.substring(7);
    } else {
      return null;
    }
  }

  @Override
  public boolean validate(String token) {
    try {
      notNull(Jwts.parser().verifyWith(generateSecretKey()).build().parse(token).getPayload());

      return false;
    }catch (ExpiredJwtException | NullPointerException e) {
      return true;
    }
  }

  @Override
  public Long getId(String token) {
    return Jwts.parser().verifyWith(generateSecretKey()).build().parseSignedClaims(token).getPayload().get("id", Long.class);
  }

  private SecretKey generateSecretKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
