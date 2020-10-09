package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Function;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class AppController implements AppManager {

  private final HashMap<String, Parent> viewCache = new HashMap<>();
  private final User user = new User("foo@foo.com", "Foo");

  @FXML
  BorderPane borderPane;
  @FXML
  Button scorecardButton;
  @FXML
  Button userButton;

  @FXML
  void initialize() {
    scorecardButton
        .setOnMouseClicked(e -> loadView("Scorecard.fxml", ScorecardController::new));

    userButton.setOnMouseClicked(e -> loadView("User.fxml", UserController::new));
  }

  @Override
  public void loadView(String fxmlName, Function<AppManager, Object> controllerFactory) {
    Parent parent;
    if (viewCache.containsKey(fxmlName)) {
      parent = viewCache.get(fxmlName);
    } else {
      var loader = new FXMLLoader(getClass().getResource(fxmlName));

      if (controllerFactory != null) {
        loader.setControllerFactory(c -> controllerFactory.apply(this));
      }

      try {
        parent = loader.load();
        viewCache.put(fxmlName, parent);
      } catch (IOException e) {
        throw new RuntimeException("Could not load FXML", e);
      }
    }

    borderPane.setCenter(parent);
  }

  @Override
  public User getUser() {
    return user;
  }
}
