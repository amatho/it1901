package golfapp.core;

import java.util.List;
import java.util.Objects;

public class Course {

  private String name;
  private List<Hole> holes;

  /**
   * Create a new course.
   *
   * @param name  the course name
   * @param holes the holes of this course
   */
  public Course(String name, List<Hole> holes) {
    this.name = name;
    this.holes = holes;
  }

  // Creator for Jackson
  private Course() {
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
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

  public int getCourseLength() {
    return holes.size();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Course) {
      var other = (Course) o;
      return name.equals(other.name) && holes.equals(other.holes);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, holes);
  }

  @Override
  public String toString() {
    return name;
  }
}
