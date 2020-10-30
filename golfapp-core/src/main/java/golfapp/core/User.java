package golfapp.core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id", scope = User.class)
public class User {

  private static final Pattern EMAIL_REGEX = Pattern
      .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

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
    setEmail(email);
    setDisplayName(displayName);
    scorecardHistory = new HashSet<>();
  }

  // Creator for Jackson
  private User() {
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    var matcher = EMAIL_REGEX.matcher(email);
    if (matcher.find()) {
      this.email = email;
    } else {
      throw new IllegalArgumentException("Invalid e-mail input");
    }
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  /**
   * Returns an unmodifiable view of the scorecard history.
   *
   * @return set of scorecards
   */
  public Set<Scorecard> getScorecardHistory() {
    return Collections.unmodifiableSet(scorecardHistory);
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
