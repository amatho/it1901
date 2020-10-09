package golfapp.gui;

import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class AppController implements LoadViewCallback {

  private final HashMap<String, Parent> viewCache = new HashMap<>();

  @FXML
  BorderPane borderPane;
  @FXML
  Button scorecardButton;
  @FXML
  Button userButton;

  @FXML
  void initialize() {
    scorecardButton
        .setOnMouseClicked(e -> loadView("Scorecard.fxml", c -> new ScorecardController(this)));

    userButton.setOnMouseClicked(e -> loadView("User.fxml", c -> new UserController(this)));
  }

  @Override
  public void loadView(String fxmlName, Callback<Class<?>, Object> controllerFactory) {
    Parent parent;
    if (viewCache.containsKey(fxmlName)) {
      parent = viewCache.get(fxmlName);
    } else {
      var loader = new FXMLLoader(getClass().getResource(fxmlName));

      if (controllerFactory != null) {
        loader.setControllerFactory(controllerFactory);
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
}
