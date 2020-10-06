package golfapp.core;

import java.util.List;

public class Course {

  private final List<Hole> holes;
  private final String courseName;
  
  public Course(String name, List<Hole> holes) {
    this.courseName = name;
    this.holes = holes;
  }

  public int getCourseLength() {
    return holes.size();
  }

  public List<Hole> getHoles() {
    return holes;
  }

  public Hole getHole(int index) {
    return holes.get(index);
  }

  public int getHoleIndex(Hole hole) {
    return holes.indexOf(hole);
  }

  public String getCourseName() { return courseName; }
}
