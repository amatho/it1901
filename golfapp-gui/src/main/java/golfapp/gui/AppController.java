package golfapp.gui;

import golfapp.core.BookingSystem;
import golfapp.core.User;
import golfapp.data.DaoFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AppController implements AppManager {

  private final User user;
  private final List<BookingSystem> bookingSystems;

  @FXML
  BorderPane borderPane;
  @FXML
  Button bookingButton;
  @FXML
  Button createScorecardButton;
  @FXML
  Button userButton;

  public AppController(User user) {
    this.user = user;
    bookingSystems = new ArrayList<>();

    // Create booking systems
    // TODO: Get booking systems from an actual data source
    DaoFactory.courseDao().getAllIgnoreId().forEach(c -> bookingSystems.add(new BookingSystem(c)));
  }

  @FXML
  void initialize() {
    bookingButton.setOnMouseClicked(e -> loadView("Booking.fxml", BookingController::new));

    createScorecardButton
        .setOnMouseClicked(e -> loadView("CreateScorecard.fxml", CreateScorecardController::new));

    userButton.setOnMouseClicked(e -> loadView("User.fxml", UserController::new));
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
  public List<BookingSystem> getBookingSystems() {
    return bookingSystems;
  }
}
