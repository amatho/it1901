package golfapp.data;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.util.Map;
import java.util.Set;

public class InMemoryGolfAppModelDao implements GolfAppModelDao {

  private final GolfAppModel model = GolfAppModel.createDefaultModel();

  @Override
  public GolfAppModel getModel() {
    return model;
  }

  @Override
  public Set<User> getUsers() {
    return model.getUsers();
  }

  @Override
  public boolean addUser(User u) {
    return model.addUser(u);
  }

  @Override
  public boolean updateUser(User u) {
    return model.updateUser(u);
  }

  @Override
  public boolean deleteUser(User u) {
    return model.deleteUser(u);
  }

  @Override
  public Set<Course> getCourses() {
    return model.getCourses();
  }

  @Override
  public Map<Course, BookingSystem> getBookingSystems() {
    return model.getBookingSystems();
  }

  @Override
  public boolean updateBookingSystem(Course c, BookingSystem b) {
    return model.updateBookingSystem(c, b);
  }
}