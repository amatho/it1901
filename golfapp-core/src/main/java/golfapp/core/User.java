package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class User {

  private final String username;
  private final UUID userId;
  private final Collection<Scorecard> scoreCardHistory;

  public User(String username) {
    this.username = username;
    this.userId = UUID.randomUUID();
    scoreCardHistory = new ArrayList<>();
  }

  @JsonCreator
  User(@JsonProperty("username") String username, @JsonProperty("userId") UUID userId,
      @JsonProperty("scoreCardHistory") Collection<Scorecard> scoreCardHistory) {
    this.username = username;
    this.userId = userId;
    this.scoreCardHistory = scoreCardHistory;
  }

  public Collection<Scorecard> getScoreCardHistory() {
    return scoreCardHistory;
  }

  public void addScorecard(Scorecard scorecard) {
    if (!scoreCardHistory.contains(scorecard)) {
      scoreCardHistory.add(scorecard);
    }
  }

  public void removeScorecard(Scorecard scorecard) {
    if (scoreCardHistory.contains(scorecard)) {
      scoreCardHistory.remove(scorecard);
    }
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
