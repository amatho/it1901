package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

  private final AppManager appManager;
  @FXML
  Label nameLabel;
  @FXML
  Label status;
  @FXML
  TextField email;
  @FXML
  TextField nameField;
  @FXML
  Button logIn;
  @FXML
  Button newUser;
  private boolean newUserIsActive;

  public LogInController() {
    this(new AppManager());
  }

  public LogInController(AppManager appManager) {
    this.appManager = appManager;
  }

  @FXML
  void initialize() {
    email.setPromptText("e-mail ...");
    nameField.setPromptText("Name ...");
    status.setText("");
    logIn.setDisable(true);
    email.textProperty()
        .addListener((observable, oldValue, newValue) -> updateLogInButton(newValue));
    nameField.setVisible(false);
    nameLabel.setVisible(false);
    status.setVisible(true);
    newUserIsActive = false;
  }

  private void updateLogInButton(String text) {
    logIn.setDisable(text.isBlank());
  }

  @FXML
  void handleNewUserButton() {
    if (newUserIsActive) {
      nameField.clear();
      nameField.setVisible(false);
      nameLabel.setVisible(false);
      newUser.setText("New User");
      logIn.setText("Log in");
    } else {
      nameField.setVisible(true);
      nameLabel.setVisible(true);
      nameField.setPromptText("Name ...");
      newUser.setText("I have a User");
      logIn.setText("Create User");
    }
    newUserIsActive = !newUserIsActive;
  }

  /**
   * Handler for login button.
   *
   * @param event the javafx event
   * @throws IOException if the e-mail is invalid
   */
  @FXML
  void handleLogIn(ActionEvent event) throws IOException {
    status.setVisible(false);

    User user;
    if (newUserIsActive) {
      try {
        user = new User(email.getText(), nameField.getText());
      } catch (User.DisplayNameException e) {
        status.setVisible(true);
        status.setText("Display name cannot be blank");
        return;
      } catch (User.EmailException e) {
        status.setVisible(true);
        status.setText("Please insert a valid email!");
        return;
      }

      var result = appManager.getModelDao().getUsers().stream()
          .filter(u -> u.getEmail().equalsIgnoreCase(user.getEmail())).findAny();
      if (result.isPresent()) {
        status.setVisible(true);
        status.setText("A user with that email already exists!");
        return;
      }

      appManager.getModelDao().addUser(user);
    } else {
      if (!User.isValidEmail(email.getText())) {
        status.setVisible(true);
        status.setText("Please insert a valid email!");
        return;
      }

      var result = appManager.getModelDao().getUsers().stream()
          .filter(u -> u.getEmail().equalsIgnoreCase(email.getText())).findAny();

      if (result.isEmpty()) {
        handleNewUserButton();
        return;
      }

      user = result.orElseThrow();
    }

    appManager.setUser(user);

    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    appManager.loadAppContainer(window);
  }
}
