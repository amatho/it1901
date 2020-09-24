package core;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseTest {

  @Test
  public void testGetCourseLength() {
    Course course1 = new Course(List.of());
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    var holes = List.of(hole1, hole2);
    Course course2 = new Course(holes);

    Assertions.assertEquals(course1.getCourseLength(), 0);
    Assertions.assertEquals(course2.getCourseLength(), 2);
  }

  @Test
  public void testGetHole() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    var holes = List.of(hole1, hole2);
    Course course = new Course(holes);

    Assertions.assertEquals(course.getHole(1), hole2);

  }

  @Test
  public void testGetHoleOutOfIndex() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    var holes = List.of(hole1, hole2);
    Course course = new Course(holes);
    try {
      course.getHole(10);
      Assertions.fail("Hole 10 do not exist");
    } catch (Exception e) {
    }
  }
}
