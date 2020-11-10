package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * A single booking, to be used in a booking system.
 */
public class Booking {

  private final User user;
  private final LocalDateTime dateTime;

  /**
   * Create a new booking.
   *
   * @param user     the user for this booking
   * @param dateTime the {@link LocalDateTime} for this booking
   */
  public Booking(User user, LocalDateTime dateTime) {
    this.user = user;
    this.dateTime = dateTime;
  }

  /**
   * Create a new booking. Used as a creator for Jackson.
   *
   * @param user     the user for this booking
   * @param dateTime the {@link LocalDateTime} for this booking
   * @return a new booking
   */
  @JsonCreator
  public static Booking createBooking(@JsonProperty("user") User user,
      @JsonProperty("dateTime") LocalDateTime dateTime) {
    return new Booking(user, dateTime);
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  /**
   * Checks if a booking has the same time as another. The user of the booking is ignored, since
   * only one booking can exist for a given time.
   *
   * @param o the object to check for equality
   * @return true if the object is a booking and has the same {@link LocalDateTime}, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Booking) {
      var other = (Booking) o;
      return other.dateTime.equals(dateTime);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return dateTime.hashCode();
  }
}
