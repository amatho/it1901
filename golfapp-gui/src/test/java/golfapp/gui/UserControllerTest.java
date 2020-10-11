package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
public class UserControllerTest {

  private UserController controller;

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
    final var loader = new FXMLLoader(getClass().getResource("User.fxml"));

    User user = new User("foo@test.com", "Foo Bar");
    Hole h1 = new Hole(1.0, 3, 1.2);
    Hole h2 = new Hole(1.0, 3, 1.2);
    LocalDateTime ldt1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 30));
    LocalDateTime ldt2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 45));
    Booking booking1 = new Booking(user.getEmail(), ldt1);
    Booking booking2 = new Booking(user.getEmail(), ldt2);
    Course c1 = new Course("Course-1", Arrays.asList(h1, h2));
    Course c2 = new Course("Course-2", Arrays.asList(h1, h2));
    BookingSystem bookingSystemC1 = new BookingSystem(c1);
    BookingSystem bookingSystemC2 = new BookingSystem(c2);
    bookingSystemC1.addBooking(booking1);
    bookingSystemC2.addBooking(booking2);
    Scorecard s1 = new Scorecard(c1, List.of(user));
    Scorecard s2 = new Scorecard(c2, List.of(user));
    user.addScorecard(s1);
    user.addScorecard(s2);

    var appManager = mock(AppManager.class);
    when(appManager.getBookingSystems()).thenReturn(List.of(bookingSystemC1, bookingSystemC2));
    when(appManager.getUser()).thenReturn(user);

    loader.setControllerFactory(c -> new UserController(appManager));
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
  void updateButton_cancelBookingButtonIsDisabledWhenNothingSelected(FxRobot robot) {
    Button cancelBooking = robot.lookup("#cancelSelectedBooking").queryButton();
    Assertions.assertTrue(cancelBooking.isDisable());
    robot.clickOn((Node) robot.lookup("#bookedTimesTableView .table-row-cell").nth(0).query());
    Assertions.assertFalse(cancelBooking.isDisable());
  }

  @Test
  void handleCancelSelectedBooking_bookingIsRemovedFromTheListView(FxRobot robot) {
    TableView<Booking> bookedTimesTableView = robot.lookup("#bookedTimesTableView")
        .queryTableView();
    Assertions.assertEquals(2, bookedTimesTableView.getItems().size());
    robot.clickOn((Node) robot.lookup("#bookedTimesTableView .table-row-cell").nth(0).query());
    robot.clickOn("#cancelSelectedBooking");
    bookedTimesTableView = robot.lookup("#bookedTimesTableView").queryTableView();
    Assertions.assertEquals(1, bookedTimesTableView.getItems().size());
  }
}
