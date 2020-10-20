package golfapp.core;

import java.time.LocalDateTime;

public class Booking {

  private String userEmail;
  private LocalDateTime dateTime;

  /**
   * Create a new booking.
   *
   * @param userEmail the {@code UUID} from the {@code User} that created this booking
   * @param dateTime  the {@code LocalDateTime} for this booking
   */
  public Booking(String userEmail, LocalDateTime dateTime) {
    this.userEmail = userEmail;
    this.dateTime = dateTime;
  }

  // Creator for Jackson
  private Booking() {
  }

  public String getUserEmail() {
    return userEmail;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Booking) {
      var other = (Booking) obj;
      return other.dateTime.equals(dateTime);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return dateTime.hashCode();
  }
}
