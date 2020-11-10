package golfapp.rest.server;

import golfapp.core.GolfAppModel;
import golfapp.rest.api.GolfAppModelService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class GolfAppConfig extends ResourceConfig {

  private GolfAppModel golfAppModel;

  /**
   * Create a Golf App config with the given model.
   *
   * @param golfAppModel the Golf App model to use
   */
  public GolfAppConfig(GolfAppModel golfAppModel) {
    setGolfAppModel(golfAppModel);
    register(GolfAppModelService.class);
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(GolfAppConfig.this.golfAppModel);
      }
    });
  }

  public GolfAppConfig() {
    this(GolfAppModel.createDefaultModel());
  }

  public GolfAppModel getGolfAppModel() {
    return golfAppModel;
  }

  public void setGolfAppModel(GolfAppModel golfAppModel) {
    this.golfAppModel = golfAppModel;
  }
}
