package golfapp.rest.api;

import golfapp.core.Course;
import golfapp.data.GolfAppModelDao;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class CourseResource {

  private final GolfAppModelDao persistenceModelDao;

  public CourseResource(GolfAppModelDao persistenceModelDao) {
    this.persistenceModelDao = persistenceModelDao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Course> getCourses() {
    return persistenceModelDao.getCourses();
  }
}
