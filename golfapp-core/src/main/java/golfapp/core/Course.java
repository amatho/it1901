package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id", scope = Course.class)
public class Course {

  private final UUID id;
  private String name;
  private final List<Hole> holes;

  /**
   * Create a new course.
   *
   * @param name  the course name
   * @param holes the holes of this course
   */
  public Course(String name, List<Hole> holes) {
    this(UUID.randomUUID(), name, holes);
  }

  private Course(UUID id, String name, List<Hole> holes) {
    this.id = id;
    setName(name);
    this.holes = new ArrayList<>(holes);
  }

  // Creator for Jackson
  @JsonCreator
  public static Course createCourse(@JsonProperty("id") UUID id, @JsonProperty("name") String name,
      @JsonProperty("holes") List<Hole> holes) {
    return new Course(id, name, holes);
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

  /**
   * Returns an unmodifiable view of the holes.
   *
   * @return list of holes
   */
  public List<Hole> getHoles() {
    return Collections.unmodifiableList(holes);
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
