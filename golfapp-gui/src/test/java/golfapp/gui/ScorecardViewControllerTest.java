package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class ScorecardViewControllerTest extends AbstractControllerTest<ScorecardViewController> {

  @Start
  void start(final Stage stage) throws IOException {
    loadFxml(stage);
  }

  @Override
  ScorecardViewController controllerFactory() {
    var appManager = mock(AppManager.class);
    User ola = new User("ola123@gmail.com", "Ola");
    User tom = new User("tom123@gmail.com", "Tom");
    User espen = new User("espen123@gmail.com", "Espen");

    List<User> users = List.of(ola, tom, espen);

    Hole hull1 = new Hole(440, 5, 20);
    Hole hull2 = new Hole(320, 4, 15);
    Hole hull3 = new Hole(130, 3, -20);
    Hole hull4 = new Hole(510, 5, 6);
    Hole hull5 = new Hole(300, 4, -10);
    Hole hull6 = new Hole(150, 3, -7);
    Hole hull7 = new Hole(332, 4, -11);
    Hole hull8 = new Hole(270, 4, 16);
    Hole hull9 = new Hole(250, 4, 20);

    List<Hole> holes = List.of(hull1, hull2, hull3, hull4, hull5,
        hull6, hull7, hull8, hull9);

    Course course = new Course("Test GK", holes);

    Scorecard scorecard = new Scorecard(course, users);
    Random rand = new Random();
    for (User u : users) {
      for (Hole h : holes) {
        int x = rand.nextInt(9) + 1;
        scorecard.setScore(u, h, x);
      }
    }

    return new ScorecardViewController(appManager, scorecard);
  }

  @Override
  String fxmlName() {
    return "ScorecardView.fxml";
  }

  @Test
  void testOutput() {
    assertEquals(240, controller.scrollPane.getHeight());
    assertEquals(660, controller.scrollPane.getWidth());
    assertEquals(6, controller.leftInfo.getChildren().size());
    assertTrue(controller.scrollPane.isFitToHeight());
  }
}


