package golfapp.gui;

import golfapp.core.BookingSystem;
import golfapp.core.User;
import java.util.List;
import java.util.function.Function;

public interface AppManager {

  /**
   * Load a view, using the given FXML name and controller factory.
   *
   * @param fxmlName          the name of the FXML file (including file ending)
   * @param controllerFactory factory function for creating a controller instance for this FXML
   */
  void loadView(String fxmlName, Function<AppManager, Object> controllerFactory);

  /**
   * Get the current logged in user.
   *
   * @return the logged in user
   */
  User getUser();

  /**
   * Get the booking systems managed by the app.
   *
   * @return a list of booking systems
   */
  List<BookingSystem> getBookingSystems();
}
