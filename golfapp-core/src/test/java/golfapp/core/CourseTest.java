package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CourseTest {


  @Test
  public void getCourseLength_returnsCorrectLength() {
    Course course1 = new Course("course1", List.of());
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(30.0, 2, 8.0);
    Course course2 = new Course("course2", List.of(hole1, hole2));

    assertEquals(0, course1.getCourseLength());
    assertEquals(2, course2.getCourseLength());
  }

  @Test
  public void getHole_returnsCorrectObject() {
    Hole hole1 = new Hole(35.0, 2, 6.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course("course", List.of(hole1, hole2));

    assertEquals(hole2, course.getHole(1));
  }

  @Test
  public void getHole_throwsWhenIndexWrong() {
    Hole hole1 = new Hole(42.0, 3, 7.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course("course", List.of(hole1, hole2));

    assertThrows(IndexOutOfBoundsException.class, () -> course.getHole(10));
  }
}
