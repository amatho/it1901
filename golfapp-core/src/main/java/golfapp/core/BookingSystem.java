package golfapp.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class BookingSystem {

  private static final List<LocalTime> VALID_TIMES = createValidTimes();
  private final Course course;
  private final List<LocalDateTime> availableTimes = new ArrayList<>();
  private final Set<Booking> bookings = new HashSet<>();

  /**
   * Creates a new booking system, using the current time as a basis for the times available for
   * booking.
   */
  public BookingSystem(Course course) {
    this.course = course;

    for (int i = 0; i < 14; i++) {
      LocalDate localDate = LocalDate.now().plusDays(i);
      for (LocalTime validTime : VALID_TIMES) {
        availableTimes.add(LocalDateTime.of(localDate, validTime));
      }
    }
  }

  private static List<LocalTime> createValidTimes() {
    var result = new ArrayList<LocalTime>();
    for (int h = 8; h < 20; h++) {
      for (int min = 0; min < 4; min++) {
        result.add(LocalTime.of(h, min * 15));
      }
    }
    return result;
  }

  public Course getCourse() {
    return course;
  }

  public Set<Booking> getBookings() {
    return bookings;
  }

  /**
   * Adds a new Booking to the booking system.
   *
   * @param booking the specified Booking
   */
  public void addBooking(Booking booking) {
    if (booking.getDateTime().toLocalDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Tried to book in the past");
    } else if (booking.getDateTime().toLocalDate().isAfter(LocalDate.now().plusDays(14))) {
      throw new IllegalArgumentException(
          "Tried to book greater than 14 days ahead");
    }

    var wasAdded = bookings.add(booking);

    if (!wasAdded) {
      throw new IllegalArgumentException("A booking already exists for this course and time");
    }

    availableTimes.remove(booking.getDateTime());

    // Also need to implement a connection to the User class
  }

  /**
   * Removes a booking from the system.
   *
   * @param booking the {@code Booking} to remove
   */
  public void removeBooking(Booking booking) {
    var wasRemoved = bookings.remove(booking);

    if (!wasRemoved) {
      throw new IllegalArgumentException("No booking found for the given course and time");
    }

    availableTimes.add(booking.getDateTime());
  }

  public List<LocalDateTime> getAllAvailableTimes() {
    return availableTimes;
  }

  public Stream<LocalDateTime> getAllBookedTimes() {
    return bookings.stream().map(Booking::getDateTime);
  }

  /**
   * Gets the available times for a specified date.
   *
   * @param date the specified date
   * @return list with available dates
   */
  public Stream<LocalDateTime> getAvailableTimes(LocalDate date) {
    return availableTimes.stream().filter(dateTime -> dateTime.toLocalDate().equals(date));
  }

  public Stream<LocalDate> getAvailableDates() {
    return availableTimes.stream()
        .map(d -> d.toLocalDate())
        .distinct();
  }

  /**
   * Gets the booked times for a specified date.
   *
   * @param date the specified date
   * @return list with booked dates
   */
  public Stream<LocalDateTime> getBookedTimes(LocalDate date) {
    return getAllBookedTimes().filter(dateTime -> dateTime.toLocalDate().equals(date));
  }
}
