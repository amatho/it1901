package golfapp.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Booking {

  private static final List<LocalTime> VALID_TIMES = createValidTimes();
  private final List<LocalDateTime> availableTimes = new ArrayList<>();
  private final HashMap<User, List<LocalDateTime>> bookedTimes = new HashMap<>();

  /**
   * Creates a new Booking.
   */
  public Booking() {
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

  public List<LocalDateTime> getAllAvailableTimes() {
    return availableTimes;
  }

  public List<LocalDateTime> getAllBookedTimes() {
    return bookedTimes.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  /**
   * Gets the available times for a specified date.
   *
   * @param date the specified date
   * @return list with available dates
   */
  public List<LocalDateTime> getAvailableTimes(LocalDate date) {
    return availableTimes.stream().filter(dateTime -> dateTime.toLocalDate().equals(date))
        .collect(Collectors.toList());
  }

  /**
   * Gets the booked times for a specified date.
   *
   * @param date the specified date
   * @return list with booked dates
   */
  public List<LocalDateTime> getBookedTimes(LocalDate date) {
    return getAllBookedTimes().stream().filter(dateTime -> dateTime.toLocalDate().equals(date))
        .collect(Collectors.toList());
  }

  /**
   * Updates availableTimes and bookedTimes after the user booked a time.
   *
   * @param time the time to book
   * @param user the user to book
   */
  public void bookTime(LocalDateTime time, User user) {
    if (!availableTimes.contains(time)) {
      throw new IllegalArgumentException("The specified time is not available for booking");
    }

    availableTimes.remove(time);

    var userBookings = Optional.ofNullable(bookedTimes.get(user)).orElseGet(ArrayList::new);
    userBookings.add(time);
    bookedTimes.put(user, userBookings);

    // Also need to implement a connection to the User class
  }

  /**
   * Gets the user that has booked a specified time.
   *
   * @param time the specified time
   * @return the booking user or null if no user has booked for this time
   */
  public User getBookingUser(LocalDateTime time) {
    for (var entry : bookedTimes.entrySet()) {
      if (entry.getValue().contains(time)) {
        return entry.getKey();
      }
    }
    return null;
  }
}
