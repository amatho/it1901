package golfapp.gui;

import golfapp.core.User;
import golfapp.data.FileGolfAppModelDao;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import java.util.function.Function;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AppController implements AppManager {

  private final GolfAppModelDao modelDao;
  private final User user;

  @FXML
  BorderPane borderPane;
  @FXML
  Button bookingButton;
  @FXML
  Button createScorecardButton;
  @FXML
  Button userButton;

  /**
   * Create an {@code AppController} for a logged in user.
   *
   * @param user the logged in user
   */
  public AppController(User user) {
    this(new FileGolfAppModelDao(), user);
  }

  public AppController(GolfAppModelDao modelDao, User user) {
    this.modelDao = modelDao;
    this.user = user;
  }

  @FXML
  void initialize() {
    bookingButton.setOnMouseClicked(e -> loadView("Booking.fxml", BookingController::new));

    createScorecardButton
        .setOnMouseClicked(e -> loadView("CreateScorecard.fxml", CreateScorecardController::new));

    userButton.setOnMouseClicked(e -> loadView("User.fxml", UserController::new));

    loadView("User.fxml", UserController::new);
  }

  @Override
  public void loadView(String fxmlName, Function<AppManager, Object> controllerFactory) {
    var loader = new FXMLLoader(getClass().getResource(fxmlName));

    if (controllerFactory != null) {
      loader.setControllerFactory(c -> controllerFactory.apply(this));
    }

    Parent parent;
    try {
      parent = loader.load();
    } catch (IOException e) {
      throw new RuntimeException("Could not load FXML", e);
    }

    borderPane.setCenter(parent);
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public GolfAppModelDao getModelDao() {
    return modelDao;
  }
}
