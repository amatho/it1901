package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class BookingControllerTest {

  private BookingController bookingController;


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
    final var loader = new FXMLLoader(getClass().getResource("Booking.fxml"));

    var appManager = mock(AppManager.class);
    var inMemoryModelDao = new InMemoryGolfAppModelDao();
    var user = new User("foo@example.com", "Foo Bar");
    when(appManager.getModelDao()).thenReturn(inMemoryModelDao);
    when(appManager.getUser()).thenReturn(user);

    loader.setControllerFactory(c -> new BookingController(appManager));

    final Parent root = loader.load();
    bookingController = loader.getController();
    final var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  void testController() {
    assertNotNull(bookingController);
  }

  @Test
  void testInitialize() {
    assertEquals(LocalDate.now(), bookingController.dateComboBox.getValue());
    assertNull(bookingController.courseComboBox.getValue());
    assertFalse(bookingController.confirmedBookingLabel.isVisible());
  }

  @Test
  void when_showAvailableTimes_isClicked(FxRobot robot) {
    robot.clickOn("#showAvailableTimes");
    assertFalse(bookingController.availableTimesComboBox.isVisible());
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertTrue(bookingController.availableTimesComboBox.isVisible());
  }

  @Test
  void when_confirmBooking_isClicked_And_availableTimesComboBox_isNull(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(bookingController.availableTimesComboBox.getValue());
    robot.clickOn("#confirmBooking");
    assertEquals("Your chosen time was not valid.",
        bookingController.confirmedBookingLabel.getText());
  }

  @Test
  void when_availableTimesComboBox_isClicked(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(bookingController.availableTimesComboBox.getValue());
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    assertEquals(LocalTime.of(9, 0), bookingController.availableTimesComboBox.getValue());
  }

  @Test
  void test_confirm_Booking(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    robot.clickOn("#availableTimesComboBox").clickOn("08:45");
    robot.clickOn("#confirmBooking");
    assertEquals("Booking confirmed", bookingController.confirmedBookingLabel.getText());
  }

  @Test
  void test_cleanBooking() {
    assertNull(bookingController.availableTimesComboBox.getValue());
    assertEquals("", bookingController.yourTimeText.getText());

  }
}
