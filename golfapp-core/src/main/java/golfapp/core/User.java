package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

public class User {

  private final String email;
  private final String displayName;
  private final Set<Scorecard> scorecardHistory;

  /**
   * Create a new user.
   *
   * @param email       the user's email
   * @param displayName the name to display for this user
   */
  public User(String email, String displayName) {
    this.email = email;
    this.displayName = displayName;
    scorecardHistory = new HashSet<>();
  }

  @JsonCreator
  User(@JsonProperty("email") String email, @JsonProperty("displayName") String displayName,
      @JsonProperty("scorecardHistory") Set<Scorecard> scorecardHistory) {
    this.email = email;
    this.displayName = displayName;
    this.scorecardHistory = scorecardHistory;
  }

  public String getEmail() {
    return email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Set<Scorecard> getScorecardHistory() {
    return scorecardHistory;
  }

  public void addScorecard(Scorecard scorecard) {
    scorecardHistory.add(scorecard);
  }

  public void removeScorecard(Scorecard scorecard) {
    scorecardHistory.remove(scorecard);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof User) {
      User other = (User) o;
      return email.equalsIgnoreCase(other.email);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return email.toLowerCase().hashCode();
  }

  @Override
  public String toString() {
    return displayName;
  }
}
