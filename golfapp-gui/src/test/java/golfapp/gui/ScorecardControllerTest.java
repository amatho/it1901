package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class ScorecardControllerTest {

  private ScorecardController controller;
  private AppManager appManagerMock;
  private Course course;

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
    final var loader = new FXMLLoader(getClass().getResource("Scorecard.fxml"));
    appManagerMock = mock(AppManager.class);
    GolfAppModelDao golfAppModelDao = new StringGolfAppModelDao();
    User user1 = new User("thisuser@email.com", "This User");
    User user2 = new User("bob@email.com", "Bob");
    User user3 = new User("phil@email.com", "Phil");
    User user4 = new User("ken@email.com", "Ken");
    golfAppModelDao.addUser(user1);
    golfAppModelDao.addUser(user2);
    golfAppModelDao.addUser(user3);
    golfAppModelDao.addUser(user4);
    when(appManagerMock.getModelDao()).thenReturn(golfAppModelDao);
    when(appManagerMock.getUser()).thenReturn((user1));
    course = golfAppModelDao.getCourses().stream().findAny().orElseThrow();
    ObservableList<User> users = FXCollections.observableArrayList(user1, user2, user3, user4);
    loader.setControllerFactory(c -> new ScorecardController(appManagerMock, users, course));
    final Parent root = loader.load();
    controller = loader.getController();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void updatePlayerInputs(FxRobot robot) {
    assertFalse(controller.finishButton.isVisible());
    int i = 0;
    for (Node n : controller.playerInputs.getChildren()) {
      PlayerScoreInput psi = (PlayerScoreInput) n;
      assertEquals(3, psi.getScore());
      robot.clickOn((Node) robot.lookup("#playerInputs > *").nth(i).lookup("+").query());
      i++;
    }
    robot.clickOn((Node) robot.lookup("#holes").lookup("2").query());
    assertTrue(controller.finishButton.isVisible());
    robot.clickOn((Node) robot.lookup("#holes").lookup("1").query());
    assertFalse(controller.finishButton.isVisible());

    for (Node n : controller.playerInputs.getChildren()) {
      PlayerScoreInput psi = (PlayerScoreInput) n;
      assertEquals(4, psi.getScore());
    }
  }

  @Test
  void updateHoleInfo(FxRobot robot) {
    Hole hole = course.getHole(controller.holes.getCurrentPageIndex());
    assertEquals("Length: " + hole.getLength(), controller.holeLength.getText());
    assertEquals("Height: " + hole.getHeight(), controller.holeHeight.getText());
    assertEquals("Par: " + hole.getPar(), controller.holePar.getText());
    assertEquals(1, controller.holes.getCurrentPageIndex() + 1);

    robot.clickOn((Node) robot.lookup("#holes").lookup("2").query());
    hole = course.getHole(controller.holes.getCurrentPageIndex());
    assertEquals("Length: " + hole.getLength(), controller.holeLength.getText());
    assertEquals("Height: " + hole.getHeight(), controller.holeHeight.getText());
    assertEquals("Par: " + hole.getPar(), controller.holePar.getText());
    assertEquals(2, controller.holes.getCurrentPageIndex() + 1);
  }
}
