package golfapp.rest.api;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.data.BookingSystemsListConverter;
import golfapp.data.MapEntry;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class BookingSystemResource {

  private final GolfAppModel golfAppModel;

  public BookingSystemResource(GolfAppModel golfAppModel) {
    this.golfAppModel = golfAppModel;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<MapEntry<Course, BookingSystem>> getBookingSystems() {
    var converter = new BookingSystemsListConverter();
    return converter.convert(golfAppModel.getBookingSystems());
  }

  /**
   * Update a booking system.
   *
   * @param bookingSystem the updated booking system
   * @param courseId      the ID of the course this booking system belongs to
   * @return true if the booking system was updated, false otherwise
   */
  @Path("{courseId}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public boolean updateBookingSystem(BookingSystem bookingSystem,
      @PathParam("courseId") UUID courseId) {
    var optional = golfAppModel.getCourses().stream()
        .filter(c -> c.getId().equals(courseId))
        .findAny();

    if (optional.isEmpty()) {
      return false;
    }

    var course = optional.orElseThrow();
    golfAppModel.updateBookingSystem(course, bookingSystem);
    return true;
  }
}
