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
  @Produces(MediaType.APPLICATION_JSON)
  public Boolean addUser(User user) {
    return persistenceModelDao.addUser(user);
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
  public Boolean updateUser(User user, @PathParam("id") UUID id) {
    if (persistenceModelDao.getUsers().stream().noneMatch(u -> u.getId().equals(id))) {
      return false;
    }

    return persistenceModelDao.updateUser(user);
  }

  /**
   * Delete a user.
   *
   * @param id ID of the user to delete
   * @return HTTP response
   */
  @Path("{id}")
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  public Boolean deleteUser(@PathParam("id") UUID id) {
    var optional = persistenceModelDao.getUsers().stream().filter(u -> u.getId().equals(id))
        .findAny();

    if (optional.isEmpty()) {
      return false;
    }

    return persistenceModelDao.deleteUser(optional.orElseThrow());
  }
}
