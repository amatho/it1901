package golfapp.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

  @FXML
  private TextField email;
  @FXML
  private TextField nameField;
  @FXML
  private Button logIn;
  @FXML
  private Button newUser;
  private boolean newUserIsActive;

  @FXML
  void initialize() {
    email.setPromptText("e-mail ...");
    nameField.setPromptText("Name ...");
    logIn.setDisable(true);
    nameField.textProperty().addListener((observable, oldValue, newValue) -> updateLogInButton());
    email.textProperty().addListener((observable, oldValue, newValue) -> updateLogInButton());
    nameField.setVisible(false);
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
  public void handleNewUserButton() {
    if (newUserIsActive) {
      nameField.clear();
      nameField.setVisible(false);
      newUser.setText("New User");
      logIn.setText("Log in");
    } else {
      nameField.setVisible(true);
      nameField.setPromptText("Name ...");
      newUser.setText("I have a User");
      logIn.setText("Create User");
    }
    newUserIsActive = !newUserIsActive;
    updateLogInButton();
  }


  @FXML
  public void handleLogIn(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("App.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }


}
