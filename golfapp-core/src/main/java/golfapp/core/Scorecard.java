package golfapp.core;

import java.util.Collection;
import java.util.HashMap;

public class Scorecard {

  private Course course;
  private HashMap<User, int[]> scorecard = new HashMap<>();

  /**
   *
   *
   * @param course
   * @param users  the users to play
   * @throws IllegalArgumentException if it´s more then 4 players
   */
  public Scorecard(Course course, Collection<User> users) {
    if (users.size() > 4) {
      throw new IllegalArgumentException("Cannot have more than four users.");
    }
    this.course = course;
    int[] score = new int[course.getCourseLength()];
    for (User u : users) {
      scorecard.put(u, score);
    }
  }

  public void setScore(User user, Hole hole, int score) {
    int holenr = course.getHoleIndex(hole);
    if (holenr == -1) {
      throw new IllegalArgumentException("The given hole is not in the course.");
    }
    if (score < 1) {
      throw new IllegalArgumentException("Score must be 1 or bigger.");
    }
    scorecard.get(user)[holenr] = score;
  }

  public int getScore(User user, Hole hole) {
    int holenr = course.getHoleIndex(hole);
    if (holenr == -1) {
      throw new IllegalArgumentException("The given hole is not in the course");
    }
    return scorecard.get(user)[holenr];
  }

  public int getTotalScore(User user) {
    int[] scoreList = scorecard.get(user);
    int totScore = 0;
    for (int i : scoreList) {
      totScore += i;
    }
    return totScore;
  }

  HashMap<User, int[]> getScoreCard() {
    return this.scorecard;
  }

  public Course getCourse() {
    return course;
  }
}
