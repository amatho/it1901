package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import golfapp.data.ScorecardListConverter;
import golfapp.data.ScorecardMapConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A scorecard for keeping track of score on a golf course.
 */
public class Scorecard {

  private final Course course;
  private final LocalDate date;

  @JsonSerialize(converter = ScorecardListConverter.class)
  @JsonDeserialize(converter = ScorecardMapConverter.class)
  private final Map<User, List<Integer>> scorecard;

  /**
   * Create a new scorecard.
   *
   * @param course the course for this scorecard
   * @param users  the users to play
   * @throws IllegalArgumentException if there are more than 4 players
   */
  public Scorecard(Course course, Collection<User> users) {
    if (users.size() > 4) {
      throw new IllegalArgumentException("Cannot have more than four users.");
    }

    this.course = course;
    scorecard = new HashMap<>();
    date = LocalDate.now();

    for (var u : users) {
      var score = new ArrayList<Integer>(course.getCourseLength());
      course.getHoles().forEach(h -> score.add(h.getPar()));
      scorecard.put(u, score);
    }
  }

  private Scorecard(Course course, Map<User, List<Integer>> scorecard, LocalDate date) {
    this.course = course;
    this.scorecard = scorecard;
    this.date = date;
  }

  // Creator for Jackson
  @JsonCreator
  public static Scorecard createScorecard(@JsonProperty("course") Course course,
      @JsonProperty("scorecard") Map<User, List<Integer>> scorecard,
      @JsonProperty("date") LocalDate date) {
    return new Scorecard(course, scorecard, date);
  }

  /**
   * Sets the score of a given user for a given hole.
   *
   * @param user  the user
   * @param hole  the hole
   * @param score the score to set
   * @throws IllegalArgumentException if the score is less than 1 or the given hole is not in the
   *                                  course
   */
  public void setScore(User user, Hole hole, int score) {
    int holeIndex = course.getHoleIndex(hole);

    if (holeIndex == -1) {
      throw new IllegalArgumentException("The given hole is not in the course.");
    } else if (score < 1) {
      throw new IllegalArgumentException("Score must be 1 or bigger.");
    }

    scorecard.get(user).set(holeIndex, score);
  }

  /**
   * Gets the score for a given user and hole.
   *
   * @param user the user
   * @param hole the hole
   * @return the user's score for this hole
   */
  public int getScore(User user, Hole hole) {
    int holeIndex = course.getHoleIndex(hole);

    if (holeIndex == -1) {
      throw new IllegalArgumentException("The given hole is not in the course");
    }

    return scorecard.get(user).get(holeIndex);
  }

  public int getTotalScore(User user) {
    var scores = scorecard.get(user);
    return scores.stream().mapToInt(Integer::intValue).sum();
  }

  public Course getCourse() {
    return course;
  }

  Map<User, List<Integer>> getScorecard() {
    return Collections.unmodifiableMap(scorecard);
  }

  public LocalDate getDate() {
    return date;
  }

  public Set<User> getUsers() {
    return Collections.unmodifiableSet(scorecard.keySet());
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Scorecard) {
      var other = (Scorecard) o;
      return course.equals(other.course) && scorecard.equals(other.scorecard) && date
          .equals(other.date);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(course, scorecard, date);
  }
}
