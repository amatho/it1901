package golfapp.gui;

import golfapp.core.User;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class CourseController {

  private final AppManager appManager;
  private final List<User> users;

  @FXML
  private VBox playerInputs;

  public CourseController(AppManager appManager, ObservableList<User> users) {
    this.appManager = appManager;
    this.users = users;
  }

  @FXML
  void initialize() {
    for (var user : users) {
      var playerInput = new PlayerScoreInput(user);
      playerInputs.getChildren().add(playerInput);
    }
  }

  @FXML
  void changeSceneButtonPushed() {
    appManager.loadView("Scorecard.fxml", ScorecardController::new);
  }
}
