package golfapp.core;

import java.util.Collection;
import java.util.HashMap;

public class Scorecard {

  private Course course;
  private HashMap<User, int[]> scorecard = new HashMap<>();

  /**
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

  /* TODO: Implement
  public void setScore(User user, Hole hole, int score) {
  }

  public int getScore(User user, Hole hole) {
    return 0;
  }*/

  public Course getCourse() {
    return course;
  }
}
