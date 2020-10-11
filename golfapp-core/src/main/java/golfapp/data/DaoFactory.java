package golfapp.data;

import golfapp.core.Course;
import golfapp.core.User;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public final class DaoFactory {

  static {
    // Load example courses from file and populate course data
    try {
      var courseDao = new FileCourseDao();

      var reader = new BufferedReader(
          new InputStreamReader(DaoFactory.class.getResourceAsStream("courses.json"),
              StandardCharsets.UTF_8));
      var courseJson = reader.lines().collect(Collectors.joining("\n"));
      reader.close();

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
