package golfapp.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class AppTest {

  private CourseController controller;

  @Start
  void start(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource("Course.fxml"));
    final Parent root = loader.load();
    controller = loader.getController();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void testController() {
    Assertions.assertNotNull(controller);
  }
}
