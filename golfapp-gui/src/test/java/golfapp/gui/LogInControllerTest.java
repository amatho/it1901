package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

  private AppManager appManagerMock;

  @Start
  void start(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
    GolfAppModelDao golfAppModelDao = new StringGolfAppModelDao();
    User user1 = new User("foo@bar.baz", "foo bar");
    golfAppModelDao.addUser(user1);
    appManagerMock = mock(AppManager.class);
    when(appManagerMock.getModelDao()).thenReturn(golfAppModelDao);

    loader.setControllerFactory(c -> new LogInController(appManagerMock));
    final Parent root = loader.load();
    controller = loader.getController();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Override
  String fxmlName() {
    return "LogIn.fxml";
  }

  @Override
  LogInController controllerFactory() {
    return new LogInController(new AppManager(new StringGolfAppModelDao()));
  }

  @Test
  void handleLogIn_givesFeedbackIfEmailIsNotValid(FxRobot robot) {
    assertTrue(controller.status.getText().isBlank());

    robot.clickOn("#email").write("foobar.baz");
    robot.clickOn("#logIn");
    assertFalse(controller.status.getText().isBlank());

    controller.email.clear();

    robot.clickOn("#email").write("foo@bar.b");
    robot.clickOn("#newUser");
    robot.clickOn("#nameField").write("foo Bar");
    robot.clickOn("#logIn");
    assertFalse(controller.status.getText().isBlank());

    robot.clickOn("#email").write("om");
    robot.clickOn("#logIn");
    verify(appManagerMock).setUser(any(User.class));
  }

  @Test
  void handleLogIn_givesFeedbackIfDisplayNameIsNotValid(FxRobot robot) {
    assertTrue(controller.status.getText().isBlank());
    robot.clickOn("#newUser");
    robot.clickOn("#email").write("foo@example.com");

    robot.clickOn("#logIn");
    assertFalse(controller.status.getText().isBlank());

    robot.clickOn("#nameField").write("     ");
    robot.clickOn("#logIn");
    assertFalse(controller.status.getText().isBlank());

    robot.clickOn("#nameField").write("Foo");
    robot.clickOn("#logIn");
    verify(appManagerMock).setUser(any(User.class));
  }

  @Test
  void handleLogIn_givesFeedbackIfEmailIsUsed(FxRobot robot) {
    assertTrue(controller.status.getText().isBlank());
    robot.clickOn("#email").write("foo@bar.baz");
    robot.clickOn("#newUser");
    robot.clickOn("#nameField").write("foo Bar Baz");
    robot.clickOn("#logIn");
    assertFalse(controller.status.getText().isBlank());
  }

  @Test
  void updateButton_disabledWhenEmailIsBlank(FxRobot robot) {
    Button logInButton = robot.lookup("#logIn").queryButton();

    assertTrue(logInButton.isDisable());

    robot.clickOn("#newUser");
    assertTrue(logInButton.isDisable());

    robot.clickOn("#nameField").write("Amandus");
    assertTrue(logInButton.isDisable());

    robot.clickOn("#email").write("ama@example.com");
    assertFalse(logInButton.isDisable());
  }

  @Test
  void handleNewUserButton_buttonsChangesNameAndNameFieldGetsVisible(FxRobot robot) {
    Button newUserButton = robot.lookup("#newUser").queryButton();
    TextField nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("New User", newUserButton.getText());
    assertFalse(nameField.isVisible());
    robot.clickOn("#newUser");
    newUserButton = robot.lookup("#newUser").queryButton();
    nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("I have a User", newUserButton.getText());
    assertTrue(nameField.isVisible());
    robot.clickOn("#newUser");
    newUserButton = robot.lookup("#newUser").queryButton();
    nameField = robot.lookup("#nameField").query();
    Assertions.assertEquals("New User", newUserButton.getText());
    assertFalse(nameField.isVisible());
  }
}
