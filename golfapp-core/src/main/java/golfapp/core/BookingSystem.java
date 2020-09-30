package golfapp.core;

import java.util.HashMap;

public class BookingSystem {

  private final HashMap<Course, Booking> bookings = new HashMap<>();

  public HashMap<Course, Booking> getBookings() {
    return bookings;
  }

  /**
   * Adds a new Booking to bookings.
   *
   * @param course the specified Course
   */
  public void addBooking(Course course) {
    bookings.computeIfAbsent(course, c -> new Booking());
  }

  public void removeBooking(Course course) {
    bookings.remove(course);
  }

  public Booking getBooking(Course course) {
    return bookings.get(course);
  }
}
