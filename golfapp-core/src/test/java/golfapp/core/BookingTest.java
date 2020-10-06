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

public class BookingTest {

  @Test
  public void bookingConstructor_createsNonEmptyAvailableTimes() {
    Booking booking = new Booking();
    assertFalse(booking.getAllAvailableTimes().isEmpty());
  }

  @Test
  public void bookTime_addsDateToBookedTimes() {
    Course course = new Course("course", List.of());
    final User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    Booking booking = new Booking();

    assertFalse(booking.getBookedTimes(localDate).contains(localDateTime));
    assertFalse(booking.getAllBookedTimes().contains(localDateTime));
    booking.bookTime(localDateTime, user);

    assertTrue(booking.getBookedTimes(localDate).contains(localDateTime));
    assertTrue(booking.getAllBookedTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_deletesDateFromAvailableTimes() {
    Course course = new Course("course",List.of());
    final User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    Booking booking = new Booking();

    assertTrue(booking.getAvailableTimes(localDate).contains(localDateTime));
    assertTrue(booking.getAllAvailableTimes().contains(localDateTime));
    booking.bookTime(localDateTime, user);

    assertFalse(booking.getAvailableTimes(localDate).contains(localDateTime));
    assertFalse(booking.getAllAvailableTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_throwsWhenBooking15DaysAhead() {
    Course course = new Course("course",List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTimeIn15Days = localDateTime.plusDays(15);
    Booking booking = new Booking();

    assertThrows(IllegalArgumentException.class, () -> booking.bookTime(dateTimeIn15Days, user));
  }

  @Test
  public void bookTime_throwsExceptionWhenTryToBookDateInThePast() {
    Course course = new Course("course",List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTime5DaysAgo = localDateTime.minusDays(5);
    Booking booking = new Booking();

    assertThrows(IllegalArgumentException.class, () -> booking.bookTime(dateTime5DaysAgo, user));
  }

  @Test
  public void getBookingUser_returnsBookingUser() {
    Course course = new Course("course",List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    Booking booking = new Booking();
    booking.bookTime(localDateTime, user);

    assertEquals(booking.getBookingUser(localDateTime), user);
  }

  @Test
  public void getBookingUser_returnNullIfNotBooked() {
    Course course = new Course("course",List.of());
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    Booking booking = new Booking();

    assertNull(booking.getBookingUser(localDateTime));
  }
}
