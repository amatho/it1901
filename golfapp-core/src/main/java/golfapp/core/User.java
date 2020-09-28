package golfapp.core;

import java.util.UUID;

public class User {

  private final String username;
  private final UUID userId;

  public User(String username) {
    this.username = username;
    this.userId = UUID.randomUUID();
  }

  @Override
  public String toString() {
    return username;
  }

  public String getUsername() {
    return username;
  }

  public UUID getUserId() {
    return userId;
  }
}
