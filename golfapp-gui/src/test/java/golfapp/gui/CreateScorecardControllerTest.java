package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class CreateScorecardControllerTest extends
    AbstractControllerTest<CreateScorecardController> {

  @Start
  void start(final Stage stage) throws IOException {
    loadFxml(stage);
  }

  @Override
  String fxmlName() {
    return "CreateScorecard.fxml";
  }

  @Override
  CreateScorecardController controllerFactory() {
    GolfAppModelDao golfAppModelDao = new StringGolfAppModelDao();
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

    AppManager appManagerMock = mock(AppManager.class);
    when(appManagerMock.getModelDao()).thenReturn(golfAppModelDao);
    when(appManagerMock.getUser()).thenReturn((user1));

    return new CreateScorecardController(appManagerMock);
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

    robot.clickOn("#userComboBox").clickOn("Phil");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(2, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userComboBox").clickOn("Ken");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(3, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userComboBox").clickOn("Bob");
    Assertions.assertFalse(controller.addUserButton.isDisable());
    robot.clickOn("#addUserButton");
    Assertions.assertTrue(controller.addUserButton.isDisable());
    Assertions.assertEquals(4, robot.lookup("#tableView").queryTableView().getItems().size());

    robot.clickOn("#userComboBox").clickOn("Jon");
    Assertions.assertTrue(controller.addUserButton.isDisable());
  }

  @Test
  void handleDeleteUserButton_viewChanges(FxRobot robot) {
    robot.clickOn("#userComboBox").clickOn("Phil");
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
  void updateUserComboBox(FxRobot robot) {
    Assertions.assertEquals(4, controller.userComboBox.getItems().size());
    robot.clickOn("#userComboBox").clickOn("Phil");
    User u = controller.userComboBox.getSelectionModel().getSelectedItem();
    robot.clickOn("#addUserButton");
    Assertions.assertEquals(3, controller.userComboBox.getItems().size());
    Assertions.assertFalse(controller.userComboBox.getItems().contains(u));

    robot.clickOn((Node) robot.lookup("#tableView .table-row-cell").nth(1).query());
    robot.clickOn("#deleteButton");
    Assertions.assertEquals(4, controller.userComboBox.getItems().size());
    Assertions.assertTrue(controller.userComboBox.getItems().contains(u));
  }

  @Test
  void updateCreateButton(FxRobot robot) {
    Assertions.assertTrue(controller.createButton.isDisable());
    robot.clickOn("#courseComboBox").clickOn("Alta GK");
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
