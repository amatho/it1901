package golfapp.rest.server;

import golfapp.data.GolfAppModelDao;
import golfapp.data.InMemoryGolfAppModelDao;
import golfapp.data.LocalGolfAppModelDao;
import golfapp.rest.api.GolfAppModelService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class GolfAppConfig extends ResourceConfig {

  /**
   * Create a Golf App config with the given model DAO.
   *
   * @param persistenceModelDao the {@link GolfAppModelDao} to use for persistence
   */
  public GolfAppConfig(GolfAppModelDao persistenceModelDao) {
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(persistenceModelDao).to(GolfAppModelDao.class);
      }
    });

    register(GolfAppModelService.class);
    register(ObjectMapperProvider.class);
    register(JacksonFeature.class);
  }

  public GolfAppConfig() {
    this(defaultGolfAppModelDao());
  }

  private static GolfAppModelDao defaultGolfAppModelDao() {
    if (Boolean.getBoolean("maven.test.integration")) {
      return new InMemoryGolfAppModelDao();
    }

    return new LocalGolfAppModelDao();
  }
}
