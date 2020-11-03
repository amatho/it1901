package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.GuestUser;
import golfapp.core.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CreateScorecardController {

  private final AppManager appManager;
  private final Set<Course> courses;

  @FXML
  Button addGuestButton;
  @FXML
  Button addUserButton;
  @FXML
  Button createButton;
  @FXML
  Button deleteButton;
  @FXML
  TableView<User> tableView;
  @FXML
  TableColumn<User, String> usernameColumn;
  @FXML
  TableColumn<User, String> emailColumn;
  @FXML
  TextField usernameField;
  @FXML
  ChoiceBox<Course> courseChoiceBox;
  @FXML
  ChoiceBox<User> userChoiceBox;

  public CreateScorecardController(AppManager appManager) {
    this.appManager = appManager;
    courses = appManager.getModelDao().getCourses();
  }

  @FXML
  void initialize() {
    usernameColumn
        .setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getDisplayName()));
    emailColumn.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getEmail()));
    courseChoiceBox.getItems().addAll(courses);
    tableView.getItems().add(appManager.getUser());
    courseChoiceBox.getSelectionModel().selectedIndexProperty()
        .addListener(c -> updateCreateButton());
    userChoiceBox.getSelectionModel().selectedIndexProperty()
        .addListener(u -> updateAddUserButton());
    tableView.getSelectionModel().selectedItemProperty().addListener(u -> {
      updateCreateButton();
      updateDeleteButton();
      updateAddUserButton();
      updateAddGuestButton();
      updateUserChoiceBox();
    });
    usernameField.textProperty().addListener(t -> updateAddGuestButton());
    userChoiceBox.getItems().addAll(appManager.getModelDao().getUsers());

    updateAddUserButton();
    updateAddGuestButton();
    updateCreateButton();
    updateDeleteButton();
    updateUserChoiceBox();
  }

  private void updateUserChoiceBox() {
    userChoiceBox.getItems().clear();
    List<User> users = appManager.getModelDao().getUsers().stream()
        .filter(u -> !tableView.getItems().contains(u))
        .collect(Collectors.toList());
    userChoiceBox.getItems().addAll(users);
  }

  private void updateCreateButton() {
    boolean disable =
        courseChoiceBox.getSelectionModel().isEmpty() || tableView.getItems().isEmpty();
    createButton.setDisable(disable);
  }

  private void updateAddGuestButton() {
    boolean disable = usernameField.getText().isBlank() || tableView.getItems().size() >= 4;
    addGuestButton.setDisable(disable);
  }

  private void updateAddUserButton() {
    boolean disable =
        userChoiceBox.getSelectionModel().isEmpty() || tableView.getItems().size() >= 4;
    addUserButton.setDisable(disable);
  }

  private void updateDeleteButton() {
    boolean disable = tableView.getSelectionModel().isEmpty();
    deleteButton.setDisable(disable);
  }

  @FXML
  void changeSceneButtonPushed() {
    appManager
        .loadView("Scorecard.fxml", c -> new ScorecardController(appManager, tableView.getItems(),
            courseChoiceBox.getValue()));
  }

  @FXML
  void handleAddGuestButton() {
    if (tableView.getItems().size() < 4) {
      User user = new GuestUser(usernameField.getText());
      tableView.getItems().add(user);
      usernameField.clear();
      updateAddUserButton();
      updateAddGuestButton();
      updateUserChoiceBox();
    }
  }

  @FXML
  void handleAddUserButton() {
    User user = userChoiceBox.getSelectionModel().getSelectedItem();
    tableView.getItems().add(user);
    updateAddUserButton();
    updateAddGuestButton();
    updateUserChoiceBox();
  }

  @FXML
  void deleteUserButtonPushed() {
    ObservableList<User> users = tableView.getItems();
    var selectedUser = tableView.getSelectionModel().getSelectedItem();

    users.remove(selectedUser);
    tableView.getSelectionModel().clearSelection();
    updateDeleteButton();
  }
}
