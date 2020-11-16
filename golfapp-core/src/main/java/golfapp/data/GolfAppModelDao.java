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

  boolean addUser(User u);

  boolean updateUser(User u);

  boolean deleteUser(User u);

  Set<Course> getCourses();

  Map<Course, BookingSystem> getBookingSystems();

  boolean updateBookingSystem(Course c, BookingSystem b);
}
