package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseController {

  private final List<User> users;

  @FXML
  private VBox playerInputs;

  public CourseController(ObservableList<User> users) {
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
  void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("ScoreCard.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }
}
