package golfapp.core;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import golfapp.data.BookingSystemsListConverter;
import golfapp.data.BookingSystemsMapConverter;
import java.util.Map;
import java.util.Set;

public class GolfAppModel {

  private Set<User> users;
  private Set<Course> courses;

  @JsonSerialize(converter = BookingSystemsListConverter.class)
  @JsonDeserialize(converter = BookingSystemsMapConverter.class)
  private Map<Course, BookingSystem> bookingSystems;

  /**
   * Creates a model with the given data.
   *
   * @param users          set of users
   * @param courses        set of courses
   * @param bookingSystems map of booking systems
   */
  public GolfAppModel(Set<User> users, Set<Course> courses,
      Map<Course, BookingSystem> bookingSystems) {
    this.users = users;
    this.courses = courses;
    this.bookingSystems = bookingSystems;
  }

  // Creator for Jackson
  private GolfAppModel() {
  }

  public Set<User> getUsers() {
    return users;
  }

  public void addUser(User user) {
    users.add(user);
  }

  public void deleteUser(User user) {
    users.remove(user);
  }

  /**
   * Finds a user in the set of users and replaces it with the given value.
   *
   * @param user the updated user
   */
  public void updateUser(User user) {
    var isEmpty = users.stream().filter(u -> u.equals(user)).findAny().map(u -> u = user).isEmpty();

    if (isEmpty) {
      throw new IllegalArgumentException("Could not find a user to update");
    }
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public Map<Course, BookingSystem> getBookingSystems() {
    return bookingSystems;
  }

  public void updateBookingSystem(Course course, BookingSystem bookingSystem) {
    bookingSystems.computeIfPresent(course, (c, bs) -> bookingSystem);
  }
}
