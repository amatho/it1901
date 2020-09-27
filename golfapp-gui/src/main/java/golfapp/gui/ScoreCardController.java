package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ScoreCardController {

  @FXML
  Button addButton;
  @FXML
  Button createButton;
  @FXML
  Button deleteButton;
  @FXML
  TableView<User> tableView;
  @FXML
  TableColumn<User, String> usernameColumn;
  @FXML
  TableColumn<User, String> userIdColumn;
  @FXML
  TextField usernameField;

  @FXML
  void initialize() {
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
  }

  @FXML
  void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("Course.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }

  @FXML
  void newUserButtonPushed() {
    User user = new User(usernameField.getText());
    tableView.getItems().add(user);
  }

  @FXML
  void deleteUserButtonPushed() {
    ObservableList<User> users = tableView.getItems();
    var selectedUser = tableView.getSelectionModel().getSelectedItem();

    users.remove(selectedUser);
  }
}
