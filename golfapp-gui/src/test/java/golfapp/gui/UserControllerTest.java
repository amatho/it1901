package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

  @BeforeEach
  void init() {
    Hole h1 = new Hole(1.0, 3, 1.2);
    Hole h2 = new Hole(1.0, 3, 1.2);
    BookingSystem bookingSystemC1 = new BookingSystem();
    BookingSystem bookingSystemC2 = new BookingSystem();
    LocalDateTime ldt1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 30));
    LocalDateTime ldt2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 45));
    Booking booking1 = new Booking(controller.getUser().getEmail(), ldt1);
    Booking booking2 = new Booking(controller.getUser().getEmail(), ldt2);
    bookingSystemC1.addBooking(booking1);
    bookingSystemC2.addBooking(booking2);
    Course c1 = new Course("Course-1", Arrays.asList(h1, h2), bookingSystemC1);
    Course c2 = new Course("Course-2", Arrays.asList(h1, h2), bookingSystemC2);
    Scorecard s1 = new Scorecard(c1, Arrays.asList(controller.getUser()));
    Scorecard s2 = new Scorecard(c2, Arrays.asList(controller.getUser()));
    controller.getUser().addScorecard(s1);
    controller.getUser().addScorecard(s2);
  }

  @Start
  void start(final Stage stage) throws IOException {
    final var loader = new FXMLLoader(getClass().getResource("Scorecard.fxml"));
    loader.setControllerFactory(c -> new ScorecardController(mock(LoadViewCallback.class)));
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
  void updateButton_cancelBookingButtonIsDisabledWhenNothingSelected() {
    //TODO: Write test
  }

  @Test
  void handleCancelSelectedBooking_bookingIsRemovedFromTheListView() {
    //TODO: Write test
  }

  @Test
  void handleAddBooking_takesYouToAddBookingScene() {
    //TODO: Write test
  }

  @Test
  void handleLogOutButton_takesYouToLogInScene() {
    //TODO: Write test
  }
}
