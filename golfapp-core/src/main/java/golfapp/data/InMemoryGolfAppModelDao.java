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
  public void addUser(User u) {
    model.addUser(u);
  }

  @Override
  public boolean updateUser(User u) {
    return model.updateUser(u);
  }

  @Override
  public void deleteUser(User u) {
    model.deleteUser(u);
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
  public void updateBookingSystem(Course c, BookingSystem b) {
    model.updateBookingSystem(c, b);
  }
}