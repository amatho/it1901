package golfapp.data;

import golfapp.core.Course;
import golfapp.core.User;
import java.nio.file.Files;
import java.nio.file.Path;

public final class DaoFactory {

  static {
    // Load example courses from file and populate course data
    try {
      var courseDao = new FileCourseDao();

      var path = Path.of(DaoFactory.class.getResource("courses.json").toURI());
      var courseJson = Files.readString(path);

      var courses = MapperInstance.getInstance().readerFor(Course.class).<Course>readValues(
          courseJson).readAll();

      courseDao.writeTs(courses);
    } catch (Exception e) {
      throw new IllegalStateException("Could not read example courses", e);
    }
  }

  private DaoFactory() {
  }

  public static Dao<User> userDao() {
    return new FileUserDao();
  }

  public static Dao<Course> courseDao() {
    return new FileCourseDao();
  }
}
