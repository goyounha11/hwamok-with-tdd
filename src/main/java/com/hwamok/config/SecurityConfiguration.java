package com.hwamok.config;

import com.hwamok.security.DefaultAuthenticationProvider;
import com.hwamok.security.JwtService;
import com.hwamok.security.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtService jwtService;

  // Filter -> UsernamePasswordFilter -> 인증,인가 완료
  // UsernamePasswordFilter를 지나갈 때 로그인을 시도한 유저
  // AccessToken이 발급이 된다면
  // 다음 요청 부터는 AccessToken을 헤더에 담아서 요청을 보냄
  // AccessToken을 해독해서 Id 값을 가져와야함
  // UsernamePasswordFilter를 지날 때 Id값을 userRepository에서 검색해서
  // User객체로 Security에게 이 객체는 로그인한 객체야라고 알려줘야함
  // Security -> SecurityContextHolder안에 Session이 존재

  // POST를 담아두는 배열
  private final static String[] POST_PERMIT_MATCHERS = Arrays.asList(
          "/auth/login", "/user"
  ).toArray(String[]::new);


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic(h -> h.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request ->
                    request.requestMatchers(HttpMethod.POST, POST_PERMIT_MATCHERS).permitAll()
            )
            .authorizeHttpRequests(request -> request.anyRequest().authenticated())
            .csrf(c -> c.disable())
            .authenticationProvider(new DefaultAuthenticationProvider())
            .addFilterBefore(new JwtTokenFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
