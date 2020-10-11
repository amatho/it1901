package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    loader.setControllerFactory(c -> new BookingController(new AppController(
        new User("amandus@gmail.com", "Amandus"))));
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
    assertEquals(LocalDate.now(), bookingController.dateChoiceBox.getValue());
    assertNull(bookingController.courseChoiceBox.getValue());
    assertFalse(bookingController.confirmedBookingLabel.isVisible());
  }

  @Test
  void when_showAvailableTimes_isClicked(FxRobot robot) {
    robot.clickOn("#showAvailableTimes");
    assertFalse(bookingController.availableTimesChoiceBox.isVisible());
    robot.clickOn("#courseChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertTrue(bookingController.availableTimesChoiceBox.isVisible());
  }

  @Test
  void when_confirmBooking_isClicked_And_avaiableTimesChoiceBox_isNull(FxRobot robot) {
    robot.clickOn("#courseChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(bookingController.availableTimesChoiceBox.getValue());
    robot.clickOn("#confirmBooking");
    assertEquals("Du har ikke valgt et gyldig tidspunkt.",
        bookingController.confirmedBookingLabel.getText());
  }

  @Test
  void when_availableTimesChoiceBox_isClicked(FxRobot robot) {
    robot.clickOn("#courseChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(bookingController.availableTimesChoiceBox.getValue());
    robot.clickOn("#availableTimesChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    assertEquals(LocalTime.of(9, 0), bookingController.availableTimesChoiceBox.getValue());
  }

  @Test
  void test_confirm_Booking(FxRobot robot) {
    robot.clickOn("#courseChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    robot.clickOn("#availableTimesChoiceBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#confirmBooking");
    assertEquals("Booking bekreftet", bookingController.confirmedBookingLabel.getText());
  }

  @Test
  void test_cleanBooking() {
    assertNull(bookingController.availableTimesChoiceBox.getValue());
    assertEquals("", bookingController.yourTimeText.getText());

  }
}
