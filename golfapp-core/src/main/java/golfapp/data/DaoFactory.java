package golfapp.data;

import golfapp.core.Course;
import golfapp.core.User;

public final class DaoFactory {

  private DaoFactory() {
  }

  public Dao<User> userDao() {
    return new FileUserDao();
  }

  public Dao<Course> courseDao() {
    return new FileCourseDao();
  }
}
