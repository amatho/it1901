package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public abstract class AbstractControllerTest<T> {

  protected T controller;

  protected void loadFxml(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource(fxmlName()));

    controller = controllerFactory();
    loader.setControllerFactory(c -> controller);

    final Parent root = loader.load();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  abstract T controllerFactory();

  abstract String fxmlName();

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

  @Test
  void testController() {
    assertNotNull(controller);
  }
}
