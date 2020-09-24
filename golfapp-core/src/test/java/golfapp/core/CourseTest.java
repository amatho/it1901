package golfapp.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseTest {

  @Test
  public void testGetCourseLength() {
    Course course1 = new Course();             
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course2 = new Course(hole1, hole2);

    Assertions.assertEquals(course1.getCourseLength(), 0);
    Assertions.assertEquals(course2.getCourseLength(), 2);
  }

  @Test
  public void testGetHole() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course(hole1, hole2);

    Assertions.assertEquals(course.getHole(1), hole2);

  }

  @Test
  public void testGetHoleOutOfIndex() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course(hole1, hole2);
    try {
      course.getHole(10);
      Assertions.fail("Hole 10 do not exist");
    } catch (Exception e) {
    }
  }
}
