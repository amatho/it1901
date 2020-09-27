package golfapp.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CourseTest {

  @Test
  public void testConstructor_createsNonEmptyAvailableTimes() {
    Course course = new Course(new ArrayList<>());
    Assertions.assertFalse(course.getAllAvailableTimes().isEmpty());
  }

  @Test
  public void testBookTime_deletesDateFromAvailableTimes() {
    Course course = new Course(new ArrayList<>());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    Assertions.assertFalse(course.getBookedTimes(localDate).contains(localDateTime));
    Assertions.assertFalse(course.getAllBookedTimes().contains(localDateTime));

    course.bookTime(localDateTime, user);

    Assertions.assertTrue(course.getBookedTimes(localDate).contains(localDateTime));
    Assertions.assertTrue(course.getAllBookedTimes().contains(localDateTime));
  }

  @Test
  public void testBookTime_addsDateToBookedTimes() {
    Course course = new Course(new ArrayList<>());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    Assertions.assertTrue(course.getAvailableTimes(localDate).contains(localDateTime));
    Assertions.assertTrue(course.getAllAvailableTimes().contains(localDateTime));

    course.bookTime(localDateTime, user);

    Assertions.assertFalse(course.getAvailableTimes(localDate).contains(localDateTime));
    Assertions.assertFalse(course.getAllAvailableTimes().contains(localDateTime));

  }

  @Test
  public void testBookTime_throwsExceptionWhenTryToBook15DaysAhead() {
    Course course = new Course(new ArrayList<>());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTimeIn15Days = localDateTime.plusDays(15);

    Assertions.assertThrows(IllegalArgumentException.class, () -> course.bookTime(dateTimeIn15Days, user));
  }

  @Test
  public void testBookTime_throwsExceptionWhenTryToBookDateInThePast() {
    Course course = new Course(new ArrayList<>());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTime5DaysAgo = localDateTime.plusDays(-5);

    Assertions.assertThrows(IllegalArgumentException.class, () -> course.bookTime(dateTime5DaysAgo, user));
  }

  @Test
  public void testGetCourseLength() {
    Course course1 = new Course(new ArrayList<>());
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    List<Hole> holes = new ArrayList<>();
    holes.add(hole1);
    holes.add(hole2);
    Course course2 = new Course(holes);

    Assertions.assertEquals(course1.getCourseLength(), 0);
    Assertions.assertEquals(course2.getCourseLength(), 2);
  }

  @Test
  public void testGetBookingUser_returnsBookingUser() {
    Course course = new Course(new ArrayList<>());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    course.bookTime(localDateTime, user);

    Assertions.assertEquals(course.getBookingUser(localDateTime), user);
  }

  @Test
  public void testGetBookingUser_throwsExceptionWhenTimeIsNotBooked() {
    Course course = new Course(new ArrayList<>());
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    Assertions.assertThrows(IllegalArgumentException.class, () -> course.getBookingUser(localDateTime));
  }

  @Test
  public void testGetHole() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    List<Hole> holes = new ArrayList<>();
    holes.add(hole1);
    holes.add(hole2);
    Course course = new Course(holes);

    Assertions.assertEquals(course.getHole(1), hole2);

  }

  @Test
  public void testGetHole_throwsIndexOutOfBoundsException() {
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    List<Hole> holes = new ArrayList<>();
    holes.add(hole1);
    holes.add(hole2);
    Course course = new Course(holes);
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> course.getHole(10));
  }
}
