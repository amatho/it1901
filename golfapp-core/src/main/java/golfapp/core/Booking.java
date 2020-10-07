package golfapp.core;

import java.time.LocalDateTime;
import java.util.UUID;

public class Booking {

  private final UUID userId;
  private final LocalDateTime dateTime;

  /**
   * Create a new booking.
   *
   * @param userId   the {@code UUID} from the {@code User} that created this booking
   * @param dateTime the {@code LocalDateTime} for this booking
   */
  public Booking(UUID userId, LocalDateTime dateTime) {
    this.userId = userId;
    this.dateTime = dateTime;
  }

  public UUID getUserId() {
    return userId;
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
