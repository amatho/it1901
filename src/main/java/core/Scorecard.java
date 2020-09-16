package core;

import java.util.HashMap;

public class Scorecard {

  private Course course;
  private HashMap<User, int[]> scorecard;


  public Scorecard(Course course, User... users) {
    if (users.length > 4) {
      throw new IllegalArgumentException("Cannot have more than four users.");
    }
    this.course = course;
    int[] score = new int[course.getCourseLength()];
    for (User u : users) {
      scorecard.put(u, score);
    }
  }


}
