package golfapp.gui;

import golfapp.core.Scorecard;
import golfapp.core.User;
import java.io.IOException;
import java.util.Collection;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserController {


  private final User user = new User("ama@example.com", "Amandus");
  @FXML
  Label username;
  @FXML
  Button viewSelectedScorecard;
  @FXML
  Button logOut;
  @FXML
  TableView<Scorecard> scorecardTableView;
  @FXML
  TableColumn<Scorecard, String> courseColumn;
  @FXML
  TableColumn<Scorecard, String> timeColumn;

  @FXML
  void initialize() {
    username.setText("Name: " + user.getDisplayName());
    courseColumn.setCellValueFactory(
        sc -> new ReadOnlyStringWrapper(sc.getValue().getCourse().getName()));
    timeColumn
        .setCellValueFactory(sc -> new ReadOnlyStringWrapper(sc.getValue().getDate().toString()));
    updateView();
  }

  private void updateView() {
    Collection<Scorecard> tmp = user.getScorecardHistory();
    scorecardTableView.getItems().removeAll();
    if (!tmp.isEmpty()) {
      for (Scorecard sc : tmp) {
        scorecardTableView.getItems().add(sc);
      }
    }
  }

  @FXML
  void handleLogOutButton(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader
        .load(getClass().getResource("LogIn.fxml")); //TODO: Implement LogIn.fxml
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }

  @FXML
  void handleViewSelectedScorecardButton(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader
        .load(getClass().getResource("ScoreCardView.fxml")); //TODO: Implement ScoreCardView.fxml
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }
}
