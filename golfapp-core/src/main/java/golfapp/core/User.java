package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {

  private final String username;
  private final UUID userId;
  private final Set<Scorecard> scoreCardHistory;

  /**
   * Create a new user.
   *
   * @param username the user's username
   */
  public User(String username) {
    this.username = username;
    this.userId = UUID.randomUUID();
    scoreCardHistory = new HashSet<>();
  }

  @JsonCreator
  User(@JsonProperty("username") String username, @JsonProperty("userId") UUID userId,
      @JsonProperty("scoreCardHistory") Set<Scorecard> scoreCardHistory) {
    this.username = username;
    this.userId = userId;
    this.scoreCardHistory = scoreCardHistory;
  }

  public Set<Scorecard> getScoreCardHistory() {
    return scoreCardHistory;
  }

  public void addScorecard(Scorecard scorecard) {
    scoreCardHistory.add(scorecard);
  }

  public void removeScorecard(Scorecard scorecard) {
    scoreCardHistory.remove(scorecard);
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
