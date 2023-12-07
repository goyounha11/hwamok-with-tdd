package com.hwamok.sercurity;

import com.hwamok.security.HwamokUser;
import com.hwamok.user.domain.User;
import com.hwamok.user.domain.UserRepository;
import fixture.UserFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceTest implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.save(UserFixture.createUser("test", "1234"));

    return new HwamokUser(user.getId());
  }
}
