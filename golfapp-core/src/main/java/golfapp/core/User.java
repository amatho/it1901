package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  private final UUID id;
  protected String email;
  private String displayName;
  private final Set<Scorecard> scorecardHistory;

  /**
   * Create a new user.
   *
   * @param email       the user's email
   * @param displayName the name to display for this user
   */
  public User(String email, String displayName) {
    this(UUID.randomUUID(), email, displayName, new HashSet<>());
  }

  private User(UUID id, String email, String displayName, Set<Scorecard> scorecardHistory) {
    this.id = id;
    setEmail(email);
    setDisplayName(displayName);
    this.scorecardHistory = scorecardHistory;
  }

  // Creator for Jackson
  @JsonCreator
  public static User createUser(@JsonProperty("id") UUID id, @JsonProperty("email") String email,
      @JsonProperty("displayName") String displayName,
      @JsonProperty("scorecardHistory") Set<Scorecard> scorecardHistory) {
    return new User(id, email, displayName, scorecardHistory);
  }

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  /**
   * Sets the user's email, checking if the provided email matches a RegEx.
   *
   * @param email the new email
   * @throws IllegalArgumentException if the given email was invalid
   */
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
    return displayName + " <" + email + "> (ID: " + id + ")";
  }
}
