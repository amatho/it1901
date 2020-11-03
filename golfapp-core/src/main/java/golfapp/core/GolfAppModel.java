package golfapp.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import golfapp.data.BookingSystemsListConverter;
import golfapp.data.BookingSystemsMapConverter;
import golfapp.data.CustomObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GolfAppModel {

  private final Set<User> users;
  private final Set<Course> courses;

  @JsonSerialize(converter = BookingSystemsListConverter.class)
  @JsonDeserialize(converter = BookingSystemsMapConverter.class)
  private final Map<Course, BookingSystem> bookingSystems;

  /**
   * Creates a model with the given data.
   *
   * @param users          set of users
   * @param courses        set of courses
   * @param bookingSystems map of booking systems
   */
  public GolfAppModel(Set<User> users, Set<Course> courses,
      Map<Course, BookingSystem> bookingSystems) {
    this.users = new HashSet<>(users);
    this.courses = new HashSet<>(courses);
    this.bookingSystems = new HashMap<>(bookingSystems);
  }

  // Creator for Jackson
  @JsonCreator
  public static GolfAppModel createGolfAppModel(@JsonProperty("users") Set<User> users,
      @JsonProperty("courses") Set<Course> courses,
      @JsonProperty("bookingSystems") Map<Course, BookingSystem> bookingSystems) {
    return new GolfAppModel(users, courses, bookingSystems);
  }

  /**
   * Returns an unmodifiable view of the users.
   *
   * @return set of users
   */
  public Set<User> getUsers() {
    return Collections.unmodifiableSet(users);
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
  public boolean updateUser(User user) {
    var wasUpdated = users.remove(user);

    if (wasUpdated) {
      users.add(user);
    }

    return wasUpdated;
  }

  /**
   * Returns an unmodifiable view of the courses.
   *
   * @return set of courses
   */
  public Set<Course> getCourses() {
    return Collections.unmodifiableSet(courses);
  }

  /**
   * Returns an unmodifiable view of the booking systems map.
   *
   * @return map of booking systems
   */
  public Map<Course, BookingSystem> getBookingSystems() {
    return Collections.unmodifiableMap(bookingSystems);
  }

  public void updateBookingSystem(Course course, BookingSystem bookingSystem) {
    bookingSystems.computeIfPresent(course, (c, bs) -> bookingSystem);
  }

  /**
   * Creates a default model.
   *
   * @return the default model
   */
  public static GolfAppModel createDefaultModel() {
    var reader = new BufferedReader(
        new InputStreamReader(GolfAppModel.class.getResourceAsStream("default-courses.json"),
            StandardCharsets.UTF_8));
    var json = reader.lines().collect(Collectors.joining("\n"));
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Set<Course> courses;
    try {
      courses = new HashSet<>(
          CustomObjectMapper.SINGLETON.readerFor(Course.class).<Course>readValues(json).readAll());
    } catch (Exception e) {
      throw new IllegalStateException("Could not read default courses", e);
    }

    var bookingSystems = new HashMap<Course, BookingSystem>(courses.size());
    courses.forEach(c -> bookingSystems.put(c, new BookingSystem()));

    return new GolfAppModel(new HashSet<>(), courses, bookingSystems);
  }
}
