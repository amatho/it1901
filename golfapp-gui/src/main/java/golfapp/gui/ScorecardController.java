package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.stage.Stage;

public class ScorecardController {

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
  //@FXML
  //TableColumn<User, ColorPicker> colorColumn;
  @FXML
  TextField usernameField;

  @FXML
  void initialize() {
    usernameColumn
        .setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getDisplayName()));
    //colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
  }

  @FXML
  void changeSceneButtonPushed(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setControllerFactory(c -> new CourseController(tableView.getItems()));
    loader.setLocation(getClass().getResource("Course.fxml"));
    Parent courseParent = loader.load();
    Scene courseScene = new Scene(courseParent);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }

  @FXML
  void newUserButtonPushed() {
    if (tableView.getItems().size() < 4) {
      // TODO: Change to email and display name
      User user = new User(usernameField.getText(), usernameField.getText());
      tableView.getItems().add(user);
      usernameField.clear();
    }
  }

  @FXML
  void deleteUserButtonPushed() {
    ObservableList<User> users = tableView.getItems();
    var selectedUser = tableView.getSelectionModel().getSelectedItem();

    users.remove(selectedUser);
  }
}
