package golfapp.gui;

import java.io.IOException;
import java.util.function.Function;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AppController implements ViewChangeRequestListener {

  private final AppManager appManager;

  @FXML
  BorderPane borderPane;
  @FXML
  Button bookingButton;
  @FXML
  Button createScorecardButton;
  @FXML
  Button userButton;

  /**
   * Create an {@code AppController}.
   *
   * @param appManager the app manager
   */
  public AppController(AppManager appManager) {
    this.appManager = appManager;
    appManager.addViewChangeRequestListener(this);
  }

  @FXML
  void initialize() {
    bookingButton
        .setOnMouseClicked(e -> appManager.loadView("Booking.fxml", BookingController::new));

    createScorecardButton.setOnMouseClicked(
        e -> appManager.loadView("CreateScorecard.fxml", CreateScorecardController::new));

    userButton.setOnMouseClicked(e -> appManager.loadView("User.fxml", UserController::new));

    appManager.loadView("User.fxml", UserController::new);
  }

  /**
   * Changes the current view that the user sees.
   *
   * @param fxmlName          name of FXML file with file extension
   * @param controllerFactory controllerFactory for this FXML
   */
  @Override
  public void viewChangeRequested(String fxmlName, Function<AppManager, Object> controllerFactory) {
    var loader = new FXMLLoader(getClass().getResource(fxmlName));

    if (controllerFactory != null) {
      loader.setControllerFactory(c -> controllerFactory.apply(appManager));
    }

    Parent parent;
    try {
      parent = loader.load();
    } catch (IOException e) {
      throw new RuntimeException("Could not load FXML", e);
    }

    borderPane.setCenter(parent);
  }
}
