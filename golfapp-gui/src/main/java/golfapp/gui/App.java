package golfapp.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage primaryStage) throws IOException {
    final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
    final Parent root = fxmlLoader.load();
    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setTitle("Golf App");
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("golf_ball.png")));
  }
}
