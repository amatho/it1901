package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class BookingSystem {

  private static final List<LocalTime> VALID_TIMES = createValidTimes();

  @JsonIgnore
  private final List<LocalDateTime> availableTimes;

  private final Set<Booking> bookings;

  /**
   * Creates a new booking system, using the current time as a basis for the times available for
   * booking.
   */
  public BookingSystem() {
    availableTimes = new ArrayList<>();
    bookings = new HashSet<>();

    for (int i = 0; i < 14; i++) {
      LocalDate localDate = LocalDate.now().plusDays(i);
      for (LocalTime validTime : VALID_TIMES) {
        availableTimes.add(LocalDateTime.of(localDate, validTime));
      }
    }
  }

  /**
   * Creates a booking system, and adds the given bookings to it.
   *
   * @param bookings the bookings to add
   * @return the created booking system
   */
  @JsonCreator
  public static BookingSystem createBookingSystem(
      @JsonProperty("bookings") final Set<Booking> bookings) {
    var bs = new BookingSystem();

    if (bookings != null) {
      bookings.stream().filter(b -> !b.getDateTime().toLocalDate().isBefore(LocalDate.now()))
          .forEach(bs::addBooking);
    }

    return bs;
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

  /**
   * Returns an unmodifiable view of the bookings.
   *
   * @return set of bookings
   */
  public Set<Booking> getBookings() {
    return Collections.unmodifiableSet(bookings);
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

  /**
   * Returns an unmodifiable view of all available times for booking.
   *
   * @return stream of all available times
   */
  public List<LocalDateTime> getAllAvailableTimes() {
    return Collections.unmodifiableList(availableTimes);
  }

  /**
   * Get the date and time of all the bookings.
   *
   * @return stream of all booked times
   */
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

  /**
   * Get available dates for booking.
   *
   * @return stream of available dates
   */
  public Stream<LocalDate> getAvailableDates() {
    return availableTimes.stream()
        .map(LocalDateTime::toLocalDate)
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

  @Override
  public boolean equals(Object o) {
    if (o instanceof BookingSystem) {
      var other = (BookingSystem) o;
      return availableTimes.equals(other.availableTimes) && bookings.equals(other.bookings);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(availableTimes, bookings);
  }
}
