package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CourseTest {

  @Test
  public void constructor_createsNonEmptyAvailableTimes() {
    Course course = new Course(List.of());

    assertFalse(course.getAllAvailableTimes().isEmpty());
  }

  @Test
  public void bookTime_addsDateToBookedTimes() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    assertFalse(course.getBookedTimes(localDate).contains(localDateTime));
    assertFalse(course.getAllBookedTimes().contains(localDateTime));
    course.bookTime(localDateTime, user);

    assertTrue(course.getBookedTimes(localDate).contains(localDateTime));
    assertTrue(course.getAllBookedTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_deletesDateFromAvailableTimes() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    assertTrue(course.getAvailableTimes(localDate).contains(localDateTime));
    assertTrue(course.getAllAvailableTimes().contains(localDateTime));
    course.bookTime(localDateTime, user);

    assertFalse(course.getAvailableTimes(localDate).contains(localDateTime));
    assertFalse(course.getAllAvailableTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_throwsWhenBooking15DaysAhead() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTimeIn15Days = localDateTime.plusDays(15);

    assertThrows(IllegalArgumentException.class, () -> course.bookTime(dateTimeIn15Days, user));
  }

  @Test
  public void bookTime_throwsExceptionWhenTryToBookDateInThePast() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTime5DaysAgo = localDateTime.minusDays(5);

    assertThrows(IllegalArgumentException.class, () -> course.bookTime(dateTime5DaysAgo, user));
  }

  @Test
  public void getBookingUser_returnsBookingUser() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    course.bookTime(localDateTime, user);

    assertEquals(course.getBookingUser(localDateTime), user);
  }

  @Test
  public void getBookingUser_returnNullIfNotBooked() {
    Course course = new Course(List.of());
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

    assertNull(course.getBookingUser(localDateTime));
  }

  @Test
  public void getCourseLength_returnsCorrectLength() {
    Course course1 = new Course(List.of());
    Hole hole1 = new Hole(50.0, 3, 10.0);
    Hole hole2 = new Hole(30.0, 2, 8.0);
    Course course2 = new Course(List.of(hole1, hole2));

    assertEquals(0, course1.getCourseLength());
    assertEquals(2, course2.getCourseLength());
  }

  @Test
  public void getHole_returnsCorrectObject() {
    Hole hole1 = new Hole(35.0, 2, 6.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course(List.of(hole1, hole2));

    assertEquals(hole2, course.getHole(1));
  }

  @Test
  public void getHole_throwsWhenIndexWrong() {
    Hole hole1 = new Hole(42.0, 3, 7.0);
    Hole hole2 = new Hole(50.0, 3, 10.0);
    Course course = new Course(List.of(hole1, hole2));

    assertThrows(IndexOutOfBoundsException.class, () -> course.getHole(10));
  }
}
