package golfapp.gui;

import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class LogInControllerTest extends AbstractControllerTest<LogInController> {

  @Start
  void start(final Stage stage) throws IOException {
    loadFxml(stage);
  }

  @Override
  String fxmlName() {
    return "LogIn.fxml";
  }

  @Override
  LogInController controllerFactory() {
    return new LogInController(new AppManager(new InMemoryGolfAppModelDao()));
  }

  @Test
  void updateButton_disabledWhenEmailAndNameIsBlank(FxRobot robot) {
    Button logInButton = robot.lookup("#logIn").queryButton();
    Assertions.assertTrue(logInButton.isDisable());
    robot.clickOn("#email").write("ama@example.com");
    logInButton = robot.lookup("#logIn").queryButton();
    Assertions.assertFalse(logInButton.isDisable());
    robot.clickOn("#newUser");
    logInButton = robot.lookup("#logIn").queryButton();
    Assertions.assertTrue(logInButton.isDisable());
    robot.clickOn("#nameField").write("Amandus");
    logInButton = robot.lookup("#logIn").queryButton();
    Assertions.assertFalse(logInButton.isDisable());
  }

  @Test
  void handleNewUserButton_buttonsChangesNameAndNameFieldGetsVisible(FxRobot robot) {
    Button newUserButton = robot.lookup("#newUser").queryButton();
    TextField nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("New User", newUserButton.getText());
    Assertions.assertFalse(nameField.isVisible());
    robot.clickOn("#newUser");
    newUserButton = robot.lookup("#newUser").queryButton();
    nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("I have a User", newUserButton.getText());
    Assertions.assertTrue(nameField.isVisible());
    robot.clickOn("#newUser");
    newUserButton = robot.lookup("#newUser").queryButton();
    nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("New User", newUserButton.getText());
    Assertions.assertFalse(nameField.isVisible());
  }

  // TODO: write test
  // @Test
  // void handleLogIn_givesFeedbackIfEmailIsNotValid() {
  // }
}
