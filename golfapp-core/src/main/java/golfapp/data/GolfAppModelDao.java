package golfapp.data;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.util.Map;
import java.util.Set;

public interface GolfAppModelDao {

  GolfAppModel getModel();

  Set<User> getUsers();

  void addUser(User u);

  boolean updateUser(User u);

  void deleteUser(User u);

  Set<Course> getCourses();

  Map<Course, BookingSystem> getBookingSystems();

  void updateBookingSystem(Course c, BookingSystem b);
}
