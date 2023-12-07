package fixture;

import com.hwamok.user.domain.User;

public class UserFixture {
  public static User createUser(String loginId, String password) {
    return new User(loginId, password, "test@gmail.com", "nickname", "name", "1993-01-15");
  }
}
