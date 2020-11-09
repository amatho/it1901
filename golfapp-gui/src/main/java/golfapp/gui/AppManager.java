package golfapp.gui;

import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import golfapp.data.LocalGolfAppModelDao;
import golfapp.data.RemoteGolfAppModelDao;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class AppManager {

  private final GolfAppModelDao modelDao;
  private final Collection<ViewChangeRequestListener> listeners = new ArrayList<>();
  private User user = null;

  public AppManager(GolfAppModelDao modelDao) {
    this.modelDao = modelDao;
  }

  public AppManager() {
    this(getDefaultModelDao());
  }

  private static GolfAppModelDao getDefaultModelDao() {
    var remoteModel = new RemoteGolfAppModelDao(URI.create("http://localhost:8080/golfapp/"));
    if (remoteModel.isAvailable()) {
      System.out.println("Using remote service");
      return remoteModel;
    }

    System.out.println("Remote service not available");
    return new LocalGolfAppModelDao();
  }

  public GolfAppModelDao getModelDao() {
    return modelDao;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /**
   * Load a view, using the given FXML name and controller factory.
   *
   * @param fxmlName          the name of the FXML file (including file ending)
   * @param controllerFactory factory function for creating a controller instance for this FXML
   */
  public void loadView(String fxmlName, Function<AppManager, Object> controllerFactory) {
    listeners.forEach(l -> l.viewChangeRequested(fxmlName, controllerFactory));
  }

  public void addViewChangeRequestListener(ViewChangeRequestListener listener) {
    listeners.add(listener);
  }
}
