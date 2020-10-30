package golfapp.rest.api;

import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class CourseResource {

  private final GolfAppModel golfAppModel;

  public CourseResource(GolfAppModel golfAppModel) {
    this.golfAppModel = golfAppModel;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Course> getCourses() {
    return golfAppModel.getCourses();
  }
}
