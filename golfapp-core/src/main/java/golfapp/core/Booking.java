package golfapp.core;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import java.time.LocalDateTime;

public class Booking {

  @JsonIdentityReference(alwaysAsId = true)
  private User user;
  private LocalDateTime dateTime;

  /**
   * Create a new booking.
   *
   * @param user     the user that created this booking
   * @param dateTime the {@link LocalDateTime} for this booking
   */
  public Booking(User user, LocalDateTime dateTime) {
    this.user = user;
    this.dateTime = dateTime;
  }

  // Creator for Jackson
  private Booking() {
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

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
