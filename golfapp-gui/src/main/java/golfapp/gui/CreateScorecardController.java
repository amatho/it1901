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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class CreateScorecardController {

  private static class UserCell extends ListCell<User> {

    @Override
    protected void updateItem(User user, boolean b) {
      super.updateItem(user, b);

      setText(user == null ? "" : user.getDisplayName());
    }
  }

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
    courseChoiceBox.getItems().addAll(courses);
    tableView.getItems().add(appManager.getUser());
    courseChoiceBox.getSelectionModel().selectedIndexProperty()
        .addListener(c -> updateCreateButton());
    userComboBox.getSelectionModel().selectedIndexProperty()
        .addListener(u -> updateAddUserButton());
    Callback<ListView<User>, ListCell<User>> cellFactory = l -> new UserCell();
    userComboBox.setCellFactory(cellFactory);
    userComboBox.setButtonCell(cellFactory.call(null));
    tableView.getSelectionModel().selectedItemProperty().addListener(u -> {
      updateCreateButton();
      updateDeleteButton();
      updateAddUserButton();
      updateAddGuestButton();
      updateUserComboBox();
    });
    usernameField.textProperty().addListener(t -> updateAddGuestButton());
    userComboBox.getItems().addAll(appManager.getModelDao().getUsers());

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
        courseChoiceBox.getSelectionModel().isEmpty() || tableView.getItems().isEmpty();
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
