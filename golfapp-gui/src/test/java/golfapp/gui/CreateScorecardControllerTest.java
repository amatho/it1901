package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import golfapp.core.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class CreateScorecardControllerTest {

  private CreateScorecardController controller;

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
    final var loader = new FXMLLoader(getClass().getResource("CreateScorecard.fxml"));
    loader.setControllerFactory(c -> new CreateScorecardController(mock(AppManager.class)));
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
  void when_addButton_is_clicked_view_changes(FxRobot robot) {
    robot.clickOn("#usernameField").write("Petter");
    robot.clickOn("#addButton");

    assertFalse(robot.lookup("#tableView").queryTableView().getItems().isEmpty());
  }

  @Test
  void when_deleteButton_is_clicked_view_changes(FxRobot robot) throws InterruptedException {
    robot.clickOn("#usernameField").write("Ola Nordmann");
    robot.clickOn("#addButton");
    robot.clickOn("#usernameField").write("Kari Karianne");
    robot.clickOn("#addButton");
    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(0).query());
    robot.clickOn("#deleteButton");

    TableView<User> table = robot.lookup("#tableView").queryTableView();
    assertEquals(1, table.getItems().size());
    assertEquals("Kari Karianne",
        table.getItems().stream().findFirst().orElseThrow().getDisplayName());
  }
}
