package golfapp.gui;

import golfapp.core.User;
import golfapp.data.Dao;
import golfapp.data.DaoFactory;
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

  private final Dao<User> userDao;
  private boolean newUserIsActive;

  @FXML
  Label nameLabel;
  @FXML
  TextField email;
  @FXML
  TextField nameField;
  @FXML
  Button logIn;
  @FXML
  Button newUser;

  public LogInController() {
    userDao = DaoFactory.userDao();
  }

  @FXML
  void initialize() {
    email.setPromptText("e-mail ...");
    nameField.setPromptText("Name ...");
    logIn.setDisable(true);
    nameField.textProperty().addListener((observable, oldValue, newValue) -> updateLogInButton());
    email.textProperty().addListener((observable, oldValue, newValue) -> updateLogInButton());
    nameField.setVisible(false);
    nameLabel.setVisible(false);

    newUserIsActive = false;
  }

  private void updateLogInButton() {
    boolean disable;
    if (newUserIsActive) {
      disable = email.getText().isBlank() || nameField.getText().isBlank();
    } else {
      disable = email.getText().isBlank();
    }
    logIn.setDisable(disable);
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
    updateLogInButton();
  }

  @FXML
  void handleLogIn(ActionEvent event) throws IOException {
    User user;
    if (newUserIsActive) {
      if (email.getText().isBlank() || nameField.getText().isBlank()) {
        return;
      }

      user = new User(email.getText(), nameField.getText());
      var userDao = DaoFactory.userDao();
      userDao.save(user);
    } else {
      var result = userDao.getAllIgnoreId()
          .filter(u -> u.getEmail().equalsIgnoreCase(email.getText())).findAny();

      if (result.isEmpty()) {
        handleNewUserButton();
        return;
      }

      user = result.orElseThrow();
    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource("App.fxml"));
    loader.setControllerFactory(c -> new AppController(user));
    Parent parent = loader.load();
    Scene scene = new Scene(parent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(scene);
    window.show();
  }
}
