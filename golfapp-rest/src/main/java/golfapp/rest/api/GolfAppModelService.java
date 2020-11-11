package golfapp.rest.api;

import golfapp.core.GolfAppModel;
import golfapp.data.GolfAppModelDao;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(GolfAppModelService.PATH)
public class GolfAppModelService {

  public static final String PATH = "golfapp";
  private final GolfAppModelDao persistenceModelDao;

  @Inject
  public GolfAppModelService(GolfAppModelDao persistenceModelDao) {
    this.persistenceModelDao = persistenceModelDao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GolfAppModel getGolfAppModel() {
    return persistenceModelDao.getModel();
  }

  @Path("users")
  public UserResource getUserResource() {
    return new UserResource(persistenceModelDao);
  }

  @Path("courses")
  public CourseResource getCourseResource() {
    return new CourseResource(persistenceModelDao);
  }

  @Path("bookingsystems")
  public BookingSystemResource getBookingSystemResource() {
    return new BookingSystemResource(persistenceModelDao);
  }
}
