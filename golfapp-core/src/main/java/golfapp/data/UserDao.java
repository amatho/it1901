package golfapp.data;

import golfapp.core.User;

public interface UserDao {

  /**
   * Save a user.
   *
   * @param user the user to save
   */
  void save(User user);

  /**
   * Load a user.
   *
   * @return the loaded user
   */
  User load();

  /**
   * Check if a user can be loaded.
   *
   * @return {@code true} if a user can be loaded, {@code false} otherwise
   */
  boolean exists();
}
