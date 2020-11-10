package golfapp.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class BookingControllerTest extends AbstractControllerTest<BookingController> {

  @Start
  void start(final Stage stage) throws IOException {
    loadFxml(stage);
  }

  @Override
  String fxmlName() {
    return "Booking.fxml";
  }

  @Override
  protected BookingController controllerFactory() {
    var appManager = mock(AppManager.class);
    var inMemoryModelDao = new InMemoryGolfAppModelDao();
    var user = new User("foo@example.com", "Foo Bar");
    when(appManager.getModelDao()).thenReturn(inMemoryModelDao);
    when(appManager.getUser()).thenReturn(user);

    return new BookingController(appManager);
  }

  @Test
  void testInitialize() {
    assertEquals(LocalDate.now(), controller.dateComboBox.getValue());
    assertNull(controller.courseComboBox.getValue());
    assertFalse(controller.confirmedBookingLabel.isVisible());
  }

  @Test
  void when_showAvailableTimes_isClicked(FxRobot robot) {
    robot.clickOn("#showAvailableTimes");
    assertFalse(controller.availableTimesComboBox.isVisible());
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertTrue(controller.availableTimesComboBox.isVisible());
  }

  @Test
  void when_confirmBooking_isClicked_And_availableTimesComboBox_isNull(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(controller.availableTimesComboBox.getValue());
    robot.clickOn("#confirmBooking");
    assertEquals("Your chosen time was not valid.",
        controller.confirmedBookingLabel.getText());
  }

  @Test
  void when_availableTimesComboBox_isClicked(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    assertNull(controller.availableTimesComboBox.getValue());
    robot.clickOn("#availableTimesComboeBox").clickOn("09:00");
    assertEquals(LocalTime.of(9, 0), controller.availableTimesComboBox.getValue());
  }

  @Test
  void test_confirm_Booking(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#showAvailableTimes");
    robot.clickOn("#availableTimesComboBox").clickOn("08:45");
    robot.clickOn("#confirmBooking");
    assertEquals("Booking confirmed", controller.confirmedBookingLabel.getText());
  }

  @Test
  void test_cleanBooking() {
    assertNull(controller.availableTimesComboBox.getValue());
    assertEquals("", controller.yourTimeText.getText());
  }
}
