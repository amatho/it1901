package golfapp.data;

import golfapp.core.Course;

public class FileCourseDao extends AbstractFileDao<Course> {

  public FileCourseDao() {
    super();
  }

  @Override
  String getFilename() {
    return "courses.json";
  }
}
