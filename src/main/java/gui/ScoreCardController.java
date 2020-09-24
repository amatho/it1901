package gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ScoreCardController {

  @FXML
  Button button;

  @FXML
  public void initialize() {
  }

  @FXML
  public void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent CourseParent = FXMLLoader.load(getClass().getResource("Course.fxml"));
    Scene CourseScene = new Scene(CourseParent);
    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    window.setScene(CourseScene);
    window.show();
  }

}
