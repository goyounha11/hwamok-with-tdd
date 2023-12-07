package com.hwamok.api;

import com.hwamok.api.dto.user.UserChangeDto;
import com.hwamok.api.dto.user.UserCreateDto;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.user.domain.User;
import com.hwamok.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  // Spring Security -> Spring의 하위 프레임워크
  // 사용하는 것만으로도 대부분의 보안 취약점을 개선
  // Filter라는 개념이 가장 중요 -> 필터를 구현해야함
  // Filter -> 요청을 맨 앞단에서 걸러줌
  // Servlet -> Filter가 존재함
  // 여러개가 중첩되어 있음
  // SpringSecurity에서의 Filter
  // Servlet의 Filter는 틀림

  @PostMapping
  public ResponseEntity<ApiResult<?>> createUser(@RequestBody UserCreateDto.Request request) {
    System.out.println("asd");
    userService.create(request.getLoginId(), request.getPassword(), request.getNickname(), request.getEmail(), request.getName(), request.getBirthDay());
    return Result.created();
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResult<User>> getUser(@PathVariable Long id) {
    User user = userService.getUser(id);
    return Result.ok(user);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResult<User>> changeUser(@PathVariable Long id, @RequestBody UserChangeDto.Request request) {
    User user = userService.changeUser(id, request.getPassword(), request.getEmail(), request.getNickname(), request.getName(), request.getBirthDay());
    return Result.ok(user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResult<?>> deleteUser(@PathVariable Long id) {
    userService.delete(id);
    return Result.ok();
  }
}
