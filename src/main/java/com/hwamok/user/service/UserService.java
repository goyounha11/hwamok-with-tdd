package com.hwamok.user.service;

import com.hwamok.user.domain.User;

public interface UserService {
  User create(String loginId, String password, String nickname, String email, String name, String birthDay);

  User getUser(Long id);

  User changeUser(Long id, String password, String email, String nickname, String name, String birthDay);

  User delete(Long id);
}
