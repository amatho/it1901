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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookingSystemTest {

  @Test
  public void bookingConstructor_createsNonEmptyAvailableTimes() {
    Booking booking = new Booking();
    assertFalse(booking.getAllAvailableTimes().isEmpty());
  }

  @Test
  public void bookingSystemConstructor_createsEmptyHashMap() {
    BookingSystem bookingSystem = new BookingSystem();
    Assertions.assertTrue(bookingSystem.getBookings().isEmpty());
  }

  @Test
  public void addBooking_addsNewBookingWithKeyCourse() {
    Course course = new Course(List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Assertions.assertTrue(bookingSystem.getBookings().containsKey(course));
    Booking booking = bookingSystem.getBooking(course);
    Assertions.assertNotEquals(null, booking);
  }

  @Test
  public void removeBooking_removesBookingFromBookings() {
    Course course = new Course(List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    bookingSystem.addBooking(course);

    Assertions.assertTrue(bookingSystem.getBookings().containsKey(course));
    bookingSystem.removeBooking(course);
    Assertions.assertFalse(bookingSystem.getBookings().containsKey(course));
  }

  @Test
  public void getBooking_returnsActualBooking() {
    Course course = new Course(List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);
    Assertions.assertNotEquals(null, booking);


  }

  @Test
  public void bookTime_addsDateToBookedTimes() {
    Course course = new Course(List.of());
    final User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);

    assertFalse(booking.getBookedTimes(localDate).contains(localDateTime));
    assertFalse(booking.getAllBookedTimes().contains(localDateTime));
    booking.bookTime(localDateTime, user);

    assertTrue(booking.getBookedTimes(localDate).contains(localDateTime));
    assertTrue(booking.getAllBookedTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_deletesDateFromAvailableTimes() {
    Course course = new Course(List.of());
    final User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);

    assertTrue(booking.getAvailableTimes(localDate).contains(localDateTime));
    assertTrue(booking.getAllAvailableTimes().contains(localDateTime));
    booking.bookTime(localDateTime, user);

    assertFalse(booking.getAvailableTimes(localDate).contains(localDateTime));
    assertFalse(booking.getAllAvailableTimes().contains(localDateTime));
  }

  @Test
  public void bookTime_throwsWhenBooking15DaysAhead() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTimeIn15Days = localDateTime.plusDays(15);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);

    assertThrows(IllegalArgumentException.class, () -> booking.bookTime(dateTimeIn15Days, user));
  }

  @Test
  public void bookTime_throwsExceptionWhenTryToBookDateInThePast() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    LocalDateTime dateTime5DaysAgo = localDateTime.minusDays(5);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);

    assertThrows(IllegalArgumentException.class, () -> booking.bookTime(dateTime5DaysAgo, user));
  }

  @Test
  public void getBookingUser_returnsBookingUser() {
    Course course = new Course(List.of());
    User user = new User("Ola");
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);
    booking.bookTime(localDateTime, user);

    assertEquals(booking.getBookingUser(localDateTime), user);
  }

  @Test
  public void getBookingUser_returnNullIfNotBooked() {
    Course course = new Course(List.of());
    LocalDate localDate = LocalDate.now();
    LocalTime localTime = LocalTime.of(8, 30);
    LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);

    assertNull(booking.getBookingUser(localDateTime));
  }

}
