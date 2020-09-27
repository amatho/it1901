package core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Course {

  private List<Hole> holes;
  private List<LocalDateTime> availableTimes = new ArrayList<>();
  private HashMap<User, List<LocalDateTime>> bookedTimes = new HashMap<>();
  private final static List<LocalTime> VALID_TIMES = createValidTimes();

  private static List<LocalTime> createValidTimes(){
    List<LocalTime> result = new ArrayList<>();
    for (int h=8; h < 20; h++) {
      for (int min = 0; min < 4; min++) {
        result.add(LocalTime.of(h,min * 15));
      }
    }
    return result;
  }

  public Course(List<Hole> holes) {
    this.holes = holes;
    // Initializes a list with all available times for the next two weeks.
    for (int i = 0; i < 14; i++) {
      LocalDate local_date = LocalDate.now().plusDays(i);
      for (LocalTime valid_time : VALID_TIMES) {
        availableTimes.add(LocalDateTime.of(local_date, valid_time));
      }

    }
  }

  public Collection<LocalDateTime> getAllAvailableTimes() {
    return availableTimes;
  }

  public Collection<LocalDateTime> getAllBookedTimes() {
    List<LocalDateTime> result = new ArrayList<>();
    for (List<LocalDateTime> times : bookedTimes.values()) {
      for (LocalDateTime time : times) {
        result.add(time);
      }
    }
    return result;
  }

  /**
   * Gets the available times for a specified date
   *
   * @param date the specified date
   * @return list with available dates
   */
  public Collection<LocalDateTime> getAvailableTimes(LocalDate date) {
    Collection<LocalDateTime> result = new ArrayList<>();
    for (LocalDateTime time : availableTimes) {
      if (time.toLocalDate().equals(date)) {
        result.add(time);
      }
    }
    return result;
  }

  /**
   * Gets the booked times for a specified date
   *
   * @param date the specified date
   * @return list with booked dates
   */
  public Collection<LocalDateTime> getBookedTimes(LocalDate date) {
    Collection<LocalDateTime> result = new ArrayList<>();
    for (LocalDateTime time : this.getAllBookedTimes()) {
      if (time.toLocalDate().equals(date)) {
        result.add(time);
      }
    }
    return result;
  }


  /**
   * Updates availableTimes and bookedTimes after the user booked a time
   *
   * @param time the time to book
   * @param user the user to book
   */
  public void bookTime(LocalDateTime time, User user) {
    if (! availableTimes.contains(time)) {
      throw new IllegalArgumentException("The specified time is not available for booking");
    }
    availableTimes.remove(time);
    if (bookedTimes.keySet().contains(user)) {
      List<LocalDateTime> tmp = bookedTimes.get(user);
      tmp.add(time);
      bookedTimes.put(user, tmp);
    }
    else {
      List<LocalDateTime> tmp = new ArrayList<>();
      tmp.add(time);
      bookedTimes.put(user, tmp);
    }
    // Also need to implement a connection to the User class
  }

  /**
   * Gets the user that has booked a specified time
   *
   * @param time the specified time
   * @return the user
   */
  public User getBookingUser(LocalDateTime time) {
    if (! this.getAllBookedTimes().contains(time)) {
      throw new IllegalArgumentException("The specified time has not been booked or is not available for booking");
    }
    for (Map.Entry<User, List<LocalDateTime>> entry : bookedTimes.entrySet()) {
      if (entry.getValue().contains(time)) {
        return entry.getKey();
      }
    }
    return null;
  }

  public int getCourseLength() {
    return holes.size();
  }

  public List<Hole> getHoles() {
    return holes;
  }

  public Hole getHole(int index) {
    return holes.get(index);
  }

  public int getHoleIndex(Hole hole) {
    return holes.indexOf(hole);
  }
}
