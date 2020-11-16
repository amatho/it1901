package golfapp.rest.api;

import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
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

  private final GolfAppModelDao persistenceModelDao;

  public UserResource(GolfAppModelDao persistenceModelDao) {
    this.persistenceModelDao = persistenceModelDao;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Set<User> getUsers() {
    return persistenceModelDao.getUsers();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addUser(User user) {
    persistenceModelDao.addUser(user);
    return Response.ok().build();
  }

  /**
   * Update a user.
   *
   * @param user the updated user
   * @param id   ID of the user to update
   * @return HTTP response
   */
  @Path("{id}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUser(User user, @PathParam("id") UUID id) {
    var exists = persistenceModelDao.getUsers().stream().anyMatch(u -> u.getId().equals(id));

    if (!exists) {
      return Response.status(Status.NOT_FOUND).build();
    }

    var wasUpdated = persistenceModelDao.updateUser(user);
    return Response.ok(wasUpdated).build();
  }

  /**
   * Delete a user.
   *
   * @param id ID of the user to delete
   * @return HTTP response
   */
  @Path("{id}")
  @DELETE
  public Response deleteUser(@PathParam("id") UUID id) {
    var optional = persistenceModelDao.getUsers().stream().filter(u -> u.getId().equals(id))
        .findAny();

    if (optional.isEmpty()) {
      return Response.status(Status.NOT_FOUND).build();
    }

    persistenceModelDao.deleteUser(optional.orElseThrow());
    return Response.ok().build();
  }
}
