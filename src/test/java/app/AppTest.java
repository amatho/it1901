package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AppTest extends ApplicationTest {
    private AppController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final var loader = new FXMLLoader(getClass().getResource("App.fxml"));
        final Parent root = loader.load();
        controller = loader.getController();
        final var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testController() {
        Assertions.assertNotNull(controller);
    }
}
