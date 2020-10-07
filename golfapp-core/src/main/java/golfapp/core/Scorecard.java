package golfapp.core;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Scorecard {

  private final Course course;
  private final HashMap<User, int[]> scorecard = new HashMap<>();
  private final LocalDate date;

  /**
   * Create a new scorecard.
   *
   * @param course the course for this scorecard
   * @param users  the users to play
   * @throws IllegalArgumentException if there are more than 4 players
   */
  public Scorecard(Course course, Collection<User> users) {
    date = LocalDate.now();
    if (users.size() > 4) {
      throw new IllegalArgumentException("Cannot have more than four users.");
    }

    this.course = course;

    for (var u : users) {
      var score = new int[course.getCourseLength()];
      scorecard.put(u, score);
    }
  }

  public void setScore(User user, Hole hole, int score) {
    int holeIndex = course.getHoleIndex(hole);

    if (holeIndex == -1) {
      throw new IllegalArgumentException("The given hole is not in the course.");
    } else if (score < 1) {
      throw new IllegalArgumentException("Score must be 1 or bigger.");
    }

    scorecard.get(user)[holeIndex] = score;
  }

  public int getScore(User user, Hole hole) {
    int holeIndex = course.getHoleIndex(hole);

    if (holeIndex == -1) {
      throw new IllegalArgumentException("The given hole is not in the course");
    }

    return scorecard.get(user)[holeIndex];
  }

  public int getTotalScore(User user) {
    var scores = scorecard.get(user);
    return Arrays.stream(scores).sum();
  }

  public Course getCourse() {
    return course;
  }

  public LocalDate getDate() {
    return date;
  }
}
