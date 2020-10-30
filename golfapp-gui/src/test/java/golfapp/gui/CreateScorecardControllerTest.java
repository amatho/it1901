package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.GolfAppModel;
import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class CreateScorecardControllerTest {

  private CreateScorecardController controller;
  private AppManager appManagerMock;

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
    appManagerMock = mock(AppManager.class);
    GolfAppModelDao golfAppModelDao = new InMemoryGolfAppModelDao();
    User user1 = new User("thisuser@email.com", "This User");
    User user2 = new User("bob@email.com", "Bob");
    User user3 = new User("phil@email.com", "Phil");
    User user4 = new User("ken@email.com", "Ken");
    User user5 = new User("jon@email.com", "Jon");
    golfAppModelDao.addUser(user1);
    golfAppModelDao.addUser(user2);
    golfAppModelDao.addUser(user3);
    golfAppModelDao.addUser(user4);
    golfAppModelDao.addUser(user5);
    when(appManagerMock.getModelDao()).thenReturn(golfAppModelDao);
    when(appManagerMock.getUser()).thenReturn((user1));
    loader.setControllerFactory(c -> new CreateScorecardController(appManagerMock));
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
  void handleAddGuestButton_GuestIsAddedAndButtonUpdated(FxRobot robot) {
    Assertions.assertTrue(controller.addGuestButton.isDisable());

    robot.clickOn("#usernameField").write("Bob");
    Assertions.assertFalse(controller.addGuestButton.isDisable());
    robot.clickOn("#addGuestButton");
    Assertions.assertTrue(controller.addGuestButton.isDisable());
    Assertions.assertEquals(2, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#usernameField").write("Phil");
    Assertions.assertFalse(controller.addGuestButton.isDisable());
    robot.clickOn("#addGuestButton");
    Assertions.assertTrue(controller.addGuestButton.isDisable());
    Assertions.assertEquals(3, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#usernameField").write("Ken");
    Assertions.assertFalse(controller.addGuestButton.isDisable());
    robot.clickOn("#addGuestButton");
    Assertions.assertTrue(controller.addGuestButton.isDisable());
    Assertions.assertEquals(4, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#usernameField").write("Jon");
    Assertions.assertTrue(controller.addGuestButton.isDisable());
  }

  @Test
  void handleAddUserButton_UserIsAddedAndButtonUpdated(FxRobot robot) {
    Assertions.assertTrue(controller.addUserButton.isDisable());

    robot.clickOn("#userChoiceBox").clickOn("Phil");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(2, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userChoiceBox").clickOn("Ken");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(3, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userChoiceBox").clickOn("Bob");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(4, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userChoiceBox").clickOn("Jon");
    Assertions.assertTrue(controller.addUserButton.isDisable());
  }

  @Test
  void handleDeleteUserButton_viewChanges(FxRobot robot) throws InterruptedException {
    robot.clickOn("#userChoiceBox").clickOn("Phil");
    robot.clickOn("#addUserButton");
    robot.clickOn("#usernameField").write("Ken");
    robot.clickOn("#addGuestButton");
    Assertions.assertTrue(controller.deleteButton.isDisable());
    Assertions.assertEquals(3, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(0).query());
    Assertions.assertFalse(controller.deleteButton.isDisable());
    robot.clickOn("#deleteButton");
    Assertions.assertTrue(controller.deleteButton.isDisable());


    Assertions.assertEquals(2, robot.lookup("#tableView").queryTableView().getItems().size());
    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(0).query());
    Assertions.assertFalse(controller.deleteButton.isDisable());
    robot.clickOn("#deleteButton");
    Assertions.assertTrue(controller.deleteButton.isDisable());

    Assertions.assertEquals(1, robot.lookup("#tableView").queryTableView().getItems().size());
    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(0).query());
    Assertions.assertFalse(controller.deleteButton.isDisable());
    robot.clickOn("#deleteButton");
    Assertions.assertTrue(controller.deleteButton.isDisable());

    Assertions.assertEquals(0, robot.lookup("#tableView").queryTableView().getItems().size());

  }

  @Test
  void updateUserChoiceBox(FxRobot robot) {
    Assertions.assertEquals(4, controller.userChoiceBox.getItems().size());
    robot.clickOn("#userChoiceBox").clickOn("Phil");
    User u = controller.userChoiceBox.getSelectionModel().getSelectedItem();
    robot.clickOn("#addUserButton");
    Assertions.assertEquals(3, controller.userChoiceBox.getItems().size());
    Assertions.assertFalse(controller.userChoiceBox.getItems().contains(u));

    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(1).query());
    robot.clickOn("#deleteButton");
    Assertions.assertEquals(4, controller.userChoiceBox.getItems().size());
    Assertions.assertTrue(controller.userChoiceBox.getItems().contains(u));
  }

  @Test
  void updateCreateButton(FxRobot robot) {
    Assertions.assertTrue(controller.createButton.isDisable());
    robot.clickOn("#courseChoiceBox").clickOn("Alta GK");
    Assertions.assertFalse(controller.createButton.isDisable());
    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(0).query());
    robot.clickOn("#deleteButton");
    Assertions.assertTrue(controller.createButton.isDisable());
  }

  @Test
  void initialize_containsCurrentUser(FxRobot robot) {
    TableView<User> table = robot.lookup("#tableView").queryTableView();
    assertEquals("This User",
        table.getItems().stream().findFirst().orElseThrow().getDisplayName());
  }
}
