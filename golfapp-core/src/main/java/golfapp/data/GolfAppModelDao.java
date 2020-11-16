package golfapp.data;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.util.Map;
import java.util.Set;

/**
 * A Data Access Object (DAO) for interacting with the data in {@link GolfAppModel}.
 */
public interface GolfAppModelDao {

  /**
   * Get the whole model.
   *
   * @return the model
   */
  GolfAppModel getModel();

  /**
   * Get the users.
   *
   * @return set of users
   */
  Set<User> getUsers();

  /**
   * Add a new user.
   *
   * @param u the user to add
   * @return true if the user was added
   */
  boolean addUser(User u);

  /**
   * Update an existing user. Uses the given user's ID to find the user to update.
   *
   * @param u the updated user
   * @return true if the user was updated
   */
  boolean updateUser(User u);

  /**
   * Delete a user.
   *
   * @param u the user to delete
   * @return true if the user was deleted
   */
  boolean deleteUser(User u);

  /**
   * Get the courses.
   *
   * @return set of courses
   */
  Set<Course> getCourses();

  /**
   * Get the map of booking systems.
   *
   * @return map of booking systems
   */
  Map<Course, BookingSystem> getBookingSystems();

  /**
   * Update a booking system.
   *
   * @param c the belonging course
   * @param b the updated booking system
   * @return true if the booking system was updated
   */
  boolean updateBookingSystem(Course c, BookingSystem b);
}
