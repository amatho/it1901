package golfapp.gui;

import golfapp.core.User;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class CourseController {

  private final LoadViewCallback viewCallback;
  private final List<User> users;

  @FXML
  private VBox playerInputs;

  public CourseController(LoadViewCallback viewCallback, ObservableList<User> users) {
    this.viewCallback = viewCallback;
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
    viewCallback.loadView("Scorecard.fxml");
  }
}
