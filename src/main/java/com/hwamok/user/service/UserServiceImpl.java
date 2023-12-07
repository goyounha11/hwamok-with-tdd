package com.hwamok.user.service;

import com.hwamok.core.exception.ExceptionCode;
import com.hwamok.core.exception.HwamokException;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User create(String loginId, String password, String nickname, String email, String name, String birthDay) {
    return userRepository.save(new User(loginId, passwordEncoder.encode(password), email, nickname, name, birthDay));
  }

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));
  }

  @Override
  public User changeUser(Long id, String password, String email, String nickname, String name, String birthDay) {
    User user = userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));

    user.change(password, email, nickname, name, birthDay);

    return user;
  }

  @Override
  public User delete(Long id) {
    User user = userRepository.findById(id).orElseThrow(() -> new HwamokException(ExceptionCode.NOT_FOUND_USER));

    return user.inactivated();
  }
}
