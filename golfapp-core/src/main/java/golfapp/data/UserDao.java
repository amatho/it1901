package golfapp.data;

import golfapp.core.User;

public interface UserDao {

  void save(User user);

  User load();
}
