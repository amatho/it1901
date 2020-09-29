package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class AppTest {

  private CourseController controller;
  private final Button addButton = new Button("Add player");
  private final Button deleteButton = new Button("Delete player(s)");
  private final TextField playerName = new TextField();
  private final TableView<User> playerView = new TableView<User>();
  private final TableColumn<User, String> playerViewList = new TableColumn<User, String>();


  @Start
  void start(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource("ScoreCard.fxml"));
    final Parent root = loader.load();
    controller = loader.getController();

    playerView.getColumns().add(playerViewList);
    playerName.setId("playerName");
    playerView.setId("playerView");
    addButton.setId("addButton");
    addButton.setOnAction(actionEvent -> playerView.getItems().add(new User(playerName.getText())));
    deleteButton.setId("deleteButton");
    deleteButton.setOnAction(actionEvent -> playerView.getItems()
                                                      .remove(playerView.getItems().size() - 1));

    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void testController() {
    Assertions.assertNotNull(controller);
  }

  @Test
  void when_addButton_is_clicked_view_changes(FxRobot robot) {
    robot.clickOn(playerName).write("Petter");
    robot.clickOn(addButton);

    Assertions.assertFalse(robot.lookup(".tableView").queryTableView().getItems().isEmpty());
  }

  @Test
  void when_deleteButton_is_clicked_view_changes(FxRobot robot) {
    robot.clickOn(playerName).write("Petter");
    robot.clickOn(addButton);
    robot.clickOn(deleteButton);

    Assertions.assertTrue(robot.lookup(".tableView").queryTableView().getItems().isEmpty());
  }
}
