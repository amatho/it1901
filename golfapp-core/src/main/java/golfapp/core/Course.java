package golfapp.core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id", scope = Course.class)
public class Course {

  private UUID id;
  private String name;
  private List<Hole> holes;

  /**
   * Create a new course.
   *
   * @param name  the course name
   * @param holes the holes of this course
   */
  public Course(String name, List<Hole> holes) {
    id = UUID.randomUUID();
    this.name = name;
    this.holes = holes;
  }

  // Creator for Jackson
  private Course() {
  }

  public UUID getId() {
    return id;
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

  public boolean deepEquals(Course other) {
    return id.equals(other.id) && name.equals(other.name) && holes.equals(other.holes);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Course) {
      var other = (Course) o;
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
    return name;
  }
}
