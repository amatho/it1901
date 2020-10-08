package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;

public class User {

  private String email;
  private String displayName;
  private Set<Scorecard> scorecardHistory;

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
  User(@JsonProperty("email") String email,
      @JsonProperty("displayName") String displayName,
      @JsonProperty("scorecardHistory") Set<Scorecard> scorecardHistory) {
    this.email = email;
    this.displayName = displayName;
    this.scorecardHistory = scorecardHistory;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public Set<Scorecard> getScorecardHistory() {
    return scorecardHistory;
  }

  public void setScorecardHistory(Set<Scorecard> scorecardHistory) {
    this.scorecardHistory = scorecardHistory;
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
    return email.hashCode();
  }

  @Override
  public String toString() {
    return displayName;
  }
}
