package golfapp.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {

  @JsonIgnore
  private final User user;
  private final Course course;
  private final LocalDateTime dateTime;

  /**
   * Create a new booking.
   *
   * @param user     the {@code User} for this Booking
   * @param course   the {@code Course} for this Booking
   * @param dateTime the {@code LocalDateTime} for this Booking
   */
  public Booking(User user, Course course, LocalDateTime dateTime) {
    this.user = user;
    this.course = course;
    this.dateTime = dateTime;
  }

  public User getUser() {
    return user;
  }

  public Course getCourse() {
    return course;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Booking) {
      var other = (Booking) obj;
      return other.course.equals(course) && other.dateTime.equals(dateTime);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(course, dateTime);
  }
}
