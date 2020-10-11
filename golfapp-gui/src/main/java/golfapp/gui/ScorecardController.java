package golfapp.gui;

import golfapp.core.User;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ScorecardController {

  private final AppManager appManager;

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

  public ScorecardController(AppManager appManager) {
    this.appManager = appManager;
  }

  @FXML
  void initialize() {
    usernameColumn
        .setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getDisplayName()));
    //colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
  }

  @FXML
  void changeSceneButtonPushed() {
    appManager
        .loadView("Course.fxml", c -> new CourseController(appManager, tableView.getItems()));
  }

  @FXML
  void newUserButtonPushed() {
    if (tableView.getItems().size() < 4) {
      // TODO: Change to support email and display name
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
