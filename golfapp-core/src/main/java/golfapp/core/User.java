package golfapp.core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id", scope = User.class)
public class User {

  private UUID id;
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
    id = UUID.randomUUID();
    this.email = email;
    this.displayName = displayName;
    scorecardHistory = new HashSet<>();
  }

  // Creator for Jackson
  private User() {
    scorecardHistory = new HashSet<>();
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
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

  public void addScorecard(Scorecard scorecard) {
    scorecardHistory.add(scorecard);
  }

  public void removeScorecard(Scorecard scorecard) {
    scorecardHistory.remove(scorecard);
  }

  public boolean deepEquals(User other) {
    return id.equals(other.id) && email.equalsIgnoreCase(other.email)
        && displayName.equals(other.displayName) && scorecardHistory.equals(other.scorecardHistory);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof User) {
      User other = (User) o;
      return id.equals(other.id);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return displayName;
  }
}
