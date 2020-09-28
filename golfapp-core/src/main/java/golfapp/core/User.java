package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class User {

  private final String username;
  private final UUID userId;

  public User(String username) {
    this.username = username;
    this.userId = UUID.randomUUID();
  }

  @JsonCreator
  User(@JsonProperty("username") String username, @JsonProperty("userId") UUID userId) {
    this.username = username;
    this.userId = userId;
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
