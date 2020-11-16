package golfapp.rest.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import golfapp.data.RemoteGolfAppModelDao;
import golfapp.gui.AppManager;
import golfapp.gui.LogInController;
import golfapp.gui.UserController;
import java.io.IOException;
import java.net.URI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
@ExtendWith(ApplicationExtension.class)
public class GolfAppModelServiceTestIT {

  @BeforeAll
  static void setupHeadless() {
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
    final var loader = new FXMLLoader(LogInController.class.getResource("LogIn.fxml"));
    loader.setControllerFactory(c -> new LogInController(new AppManager(new RemoteGolfAppModelDao(
        URI.create("http://localhost:8080/golfapp/")))));
    final Parent root = loader.load();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void logIn(FxRobot robot) {
    robot.clickOn("#newUser");
    robot.clickOn("#email").write("foo@test.com");
    robot.clickOn("#nameField").write("Foo");
    robot.clickOn("#logIn");

    final var previousTableSize = robot.lookup("#bookedTimesTableView")
        .queryTableView().getItems().size();

    robot.clickOn("#bookingButton");
    robot.clickOn("#courseComboBox").clickOn("Trondheim GK (9-holes)");
    robot.clickOn("#dateComboBox").type(KeyCode.DOWN).type(KeyCode.ENTER);
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    robot.clickOn("#confirmBooking");

    robot.clickOn("#userButton");
    TableView<UserController.BookingTableEntry> table = robot.lookup("#bookedTimesTableView")
        .queryTableView();
    assertEquals(previousTableSize + 1, table.getItems().size());
  }
}
