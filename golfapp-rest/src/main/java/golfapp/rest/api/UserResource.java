package golfapp.rest.api;

import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.util.Set;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UserResource {

  private final GolfAppModel golfAppModel;

  public UserResource(GolfAppModel golfAppModel) {
    this.golfAppModel = golfAppModel;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Set<User> getUsers() {
    return golfAppModel.getUsers();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addUser(User user) {
    golfAppModel.addUser(user);
    return Response.ok().build();
  }

  @Path("{id}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUser(User user, @PathParam("id") UUID id) {
    var exists = golfAppModel.getUsers().stream().anyMatch(u -> u.getId().equals(id));

    if (!exists) {
      return Response.status(Status.NOT_FOUND).build();
    }

    var wasUpdated = golfAppModel.updateUser(user);
    return Response.ok(wasUpdated).build();
  }

  @Path("{id}")
  @DELETE
  public Response deleteUser(@PathParam("id") UUID id) {
    var optional = golfAppModel.getUsers().stream().filter(u -> u.getId().equals(id)).findAny();

    if (optional.isEmpty()) {
      return Response.status(Status.NOT_FOUND).build();
    }

    golfAppModel.deleteUser(optional.orElseThrow());
    return Response.ok().build();
  }
}
