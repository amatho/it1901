package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.GuestUser;
import golfapp.core.User;
import golfapp.gui.cell.CourseCell;
import golfapp.gui.cell.UserCell;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
  ComboBox<Course> courseComboBox;
  @FXML
  ComboBox<User> userComboBox;

  public CreateScorecardController(AppManager appManager) {
    this.appManager = appManager;
    courses = appManager.getModelDao().getCourses();
  }

  @FXML
  void initialize() {
    usernameColumn
        .setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getDisplayName()));
    emailColumn.setCellValueFactory(cd -> new ReadOnlyStringWrapper(cd.getValue().getEmail()));

    courseComboBox.getItems().addAll(courses);
    courseComboBox.getSelectionModel().selectedIndexProperty()
        .addListener(c -> updateCreateButton());
    courseComboBox.setCellFactory(l -> new CourseCell());
    courseComboBox.setButtonCell(new CourseCell());

    userComboBox.getSelectionModel().selectedIndexProperty()
        .addListener(u -> updateAddUserButton());
    userComboBox.setCellFactory(l -> new UserCell());
    userComboBox.setButtonCell(new UserCell());
    userComboBox.getItems().addAll(appManager.getModelDao().getUsers());

    tableView.getItems().add(appManager.getUser());
    tableView.getSelectionModel().selectedItemProperty().addListener(u -> {
      updateCreateButton();
      updateDeleteButton();
      updateAddUserButton();
      updateAddGuestButton();
      updateUserComboBox();
    });

    usernameField.textProperty().addListener(t -> updateAddGuestButton());

    updateAddUserButton();
    updateAddGuestButton();
    updateCreateButton();
    updateDeleteButton();
    updateUserComboBox();
  }

  private void updateUserComboBox() {
    userComboBox.getItems().clear();
    List<User> users = appManager.getModelDao().getUsers().stream()
        .filter(u -> !tableView.getItems().contains(u))
        .collect(Collectors.toList());
    userComboBox.getItems().addAll(users);
  }

  private void updateCreateButton() {
    boolean disable =
        courseComboBox.getSelectionModel().isEmpty() || tableView.getItems().isEmpty();
    createButton.setDisable(disable);
  }

  private void updateAddGuestButton() {
    boolean disable = usernameField.getText().isBlank() || tableView.getItems().size() >= 4;
    addGuestButton.setDisable(disable);
  }

  private void updateAddUserButton() {
    boolean disable =
        userComboBox.getSelectionModel().isEmpty() || tableView.getItems().size() >= 4;
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
            courseComboBox.getValue()));
  }

  @FXML
  void handleAddGuestButton() {
    if (tableView.getItems().size() < 4) {
      User user = new GuestUser(usernameField.getText());
      tableView.getItems().add(user);
      usernameField.clear();
      updateAddUserButton();
      updateAddGuestButton();
      updateUserComboBox();
    }
  }

  @FXML
  void handleAddUserButton() {
    User user = userComboBox.getSelectionModel().getSelectedItem();
    tableView.getItems().add(user);
    updateAddUserButton();
    updateAddGuestButton();
    updateUserComboBox();
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
