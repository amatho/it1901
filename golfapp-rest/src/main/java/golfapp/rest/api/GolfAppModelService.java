package golfapp.rest.api;

import golfapp.core.GolfAppModel;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path(GolfAppModelService.PATH)
public class GolfAppModelService {

  public static final String PATH = "golfapp";
  private final GolfAppModel golfAppModel;

  @Inject
  public GolfAppModelService(GolfAppModel golfAppModel) {
    this.golfAppModel = golfAppModel;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public GolfAppModel getGolfAppModel() {
    return golfAppModel;
  }

}
