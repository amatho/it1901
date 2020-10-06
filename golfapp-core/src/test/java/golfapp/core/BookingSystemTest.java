package golfapp.core;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BookingSystemTest {

  @Test
  public void bookingSystemConstructor_createsEmptyHashMap() {
    BookingSystem bookingSystem = new BookingSystem();
    Assertions.assertTrue(bookingSystem.getBookings().isEmpty());
  }

  @Test
  public void addBooking_addsNewBookingWithKeyCourse() {
    Course course = new Course("course", List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Assertions.assertTrue(bookingSystem.getBookings().containsKey(course));
    Booking booking = bookingSystem.getBooking(course);
    Assertions.assertNotEquals(null, booking);
  }

  @Test
  public void removeBooking_removesBookingFromBookings() {
    Course course = new Course("course", List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    bookingSystem.addBooking(course);

    Assertions.assertTrue(bookingSystem.getBookings().containsKey(course));
    bookingSystem.removeBooking(course);
    Assertions.assertFalse(bookingSystem.getBookings().containsKey(course));
  }

  @Test
  public void getBooking_doesNotReturnNull() {
    Course course = new Course("course", List.of());
    BookingSystem bookingSystem = new BookingSystem();
    bookingSystem.addBooking(course);
    Booking booking = bookingSystem.getBooking(course);
    Assertions.assertNotEquals(null, booking);
  }


}
