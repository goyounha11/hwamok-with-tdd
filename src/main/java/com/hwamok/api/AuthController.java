package com.hwamok.api;

import com.hwamok.api.dto.auth.LoginDto;
import com.hwamok.auth.service.AuthService;
import com.hwamok.core.response.ApiResult;
import com.hwamok.core.response.Result;
import com.hwamok.util.Triple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResult<LoginDto.Response>> login(@RequestBody LoginDto.Request request) {
    Pair<String, String> pair = authService.login(request.getLoginId(), request.getPassword());
    return Result.ok(new LoginDto.Response(pair.getFirst(), pair.getSecond()));
  }
}
