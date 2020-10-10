package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class LogInControllerTest {

  private LogInController controller;

  @BeforeAll
  static void headless() {
    if (Boolean.getBoolean("gitlab-ci")) {
      System.setProperty("java.awt.headless", "true");
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
    }
  }

  @Start
  void start(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
    loader.setControllerFactory(c -> new LogInController(mock(LoadViewCallback.class)));
    final Parent root = loader.load();
    controller = loader.getController();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void testController() {
    assertNotNull(controller);
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
  void handleNewUserButton_buttonsChangesNameAndNameFieldGetsVisible(){
    //TODO: write test
  }

  @Test
  void handleLogIn_givesFeedbackIfEmailIsNotValid(){
    //TODO: write test
  }
}

