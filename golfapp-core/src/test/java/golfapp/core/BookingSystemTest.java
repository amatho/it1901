package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class BookingSystemTest {

  private LocalDateTime dateTimeOfToday(int hour, int minute) {
    return LocalDate.now().atTime(hour, minute);
  }

  @Test
  public void constructor_createsEmptyHashMap() {
    BookingSystem bookingSystem = new BookingSystem();
    assertTrue(bookingSystem.getBookings().isEmpty());
  }

  @Test
  public void addBooking_addsNewBookingWithKeyCourse() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Foo");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime = dateTimeOfToday(10, 15);
    Booking booking = new Booking(user, course, dateTime);

    bookingSystem.addBooking(booking);

    assertTrue(bookingSystem.getBookings().contains(booking));
  }

  @Test
  public void removeBooking_removesBookingFromBookings() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Foo Bar");
    Course course = new Course("course", List.of());
    Booking booking1 = new Booking(user, course, dateTimeOfToday(11, 30));
    Booking booking2 = new Booking(user, course, dateTimeOfToday(10, 45));

    bookingSystem.addBooking(booking1);
    bookingSystem.addBooking(booking2);
    bookingSystem.removeBooking(booking1);

    assertTrue(bookingSystem.getBookings().contains(booking2));
    assertFalse(bookingSystem.getBookings().contains(booking1));
  }

  @Test
  public void addBooking_addsDateToBookedTimes() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime = dateTimeOfToday(8, 30);
    Booking booking = new Booking(user, course, dateTime);

    assertFalse(bookingSystem.getBookedTimes(dateTime.toLocalDate()).collect(Collectors.toList())
        .contains(dateTime));
    assertFalse(bookingSystem.getAllBookedTimes().collect(Collectors.toList()).contains(dateTime));

    bookingSystem.addBooking(booking);

    assertTrue(bookingSystem.getBookedTimes(dateTime.toLocalDate()).collect(Collectors.toList())
        .contains(dateTime));
    assertTrue(bookingSystem.getAllBookedTimes().collect(Collectors.toList()).contains(dateTime));
  }

  @Test
  public void addBooking_deletesDateFromAvailableTimes() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime = dateTimeOfToday(9, 15);
    Booking booking = new Booking(user, course, dateTime);

    assertTrue(bookingSystem.getAvailableTimes(dateTime.toLocalDate()).collect(Collectors.toList())
        .contains(dateTime));
    assertTrue(bookingSystem.getAllAvailableTimes().contains(dateTime));
    bookingSystem.addBooking(booking);

    assertFalse(bookingSystem.getAvailableTimes(dateTime.toLocalDate()).collect(Collectors.toList())
        .contains(dateTime));
    assertFalse(bookingSystem.getAllAvailableTimes().contains(dateTime));
  }

  @Test
  public void addBooking_throwsWhenBooking15DaysAhead() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTimeIn15Days = dateTimeOfToday(8, 30).plusDays(15);
    Booking booking = new Booking(user, course, dateTimeIn15Days);

    assertThrows(IllegalArgumentException.class, () -> bookingSystem.addBooking(booking));
  }

  @Test
  public void addBooking_throwsExceptionWhenTryToBookDateInThePast() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime5DaysAgo = dateTimeOfToday(8, 30).minusDays(5);
    Booking booking = new Booking(user, course, dateTime5DaysAgo);

    assertThrows(IllegalArgumentException.class, () -> bookingSystem.addBooking(booking));
  }

  @Test
  public void getBookingUser_returnsBookingUser() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime = dateTimeOfToday(8, 30);
    Booking booking = new Booking(user, course, dateTime);

    bookingSystem.addBooking(booking);

    assertEquals(user, bookingSystem.getBookingUser(dateTime));
  }

  @Test
  public void getBookingUser_returnNullIfNotBooked() {
    BookingSystem bookingSystem = new BookingSystem();
    User user = new User("Ola");
    Course course = new Course("course", List.of());
    LocalDateTime dateTime = dateTimeOfToday(8, 30);
    Booking booking = new Booking(user, course, dateTime);

    bookingSystem.addBooking(booking);

    assertNull(bookingSystem.getBookingUser(dateTimeOfToday(9, 30)));
  }
}
