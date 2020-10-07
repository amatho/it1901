package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class User {

  private final String username;
  private final UUID userId;
  private final Collection<Scorecard> scoreCardHistory;
  private final Collection<Booking> bookedTimes;

  public User(String username) {
    this.username = username;
    this.userId = UUID.randomUUID();
    scoreCardHistory = new ArrayList<>();
    bookedTimes = new ArrayList<>();
  }

  @JsonCreator
  User(@JsonProperty String username, @JsonProperty UUID userId,
      @JsonProperty Collection<Scorecard> scoreCardHistory,
      @JsonProperty Collection<Booking> bookedTimes) {
    this.username = username;
    this.userId = userId;
    this.scoreCardHistory = scoreCardHistory;
    this.bookedTimes = bookedTimes;
  }

  public Collection<Booking> getBookedTimes() {
    return bookedTimes;
  }

  public void addBooking(Booking booking) {
    if (bookedTimes.contains(booking)) {
      throw new IllegalArgumentException("This booking has already been booked.");
    }
    bookedTimes.add(booking);
  }

  public void removeBooking(Booking booking) {
    if (!bookedTimes.contains(booking)) {
      throw new IllegalArgumentException("You have not booked this Booking");
    }
    bookedTimes.remove(booking);
  }

  public Collection<Scorecard> getScoreCardHistory() {
    return scoreCardHistory;
  }

  public void addScorecard(Scorecard scorecard) {
    if (!scoreCardHistory.contains(scorecard)) {
      scoreCardHistory.add(scorecard);
    }
  }

  public void removeScorecard(Scorecard scorecard) {
    scoreCardHistory.remove(scorecard);
  }

  @Override
  public String toString() {
    return username;
  }

  public String getUsername() {
    return username;
  }

  public UUID getUserId() {
    return userId;
  }
}
