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
    var inMemoryModelDao = new StringGolfAppModelDao();
    var user = new User("foo@example.com", "Foo Bar");
    when(appManager.getModelDao()).thenReturn(inMemoryModelDao);
    when(appManager.getUser()).thenReturn(user);

    return new BookingController(appManager);
  }

  @Test
  void testInitialize() {
    assertEquals(LocalDate.now(), controller.dateComboBox.getValue());
    assertNull(controller.courseComboBox.getValue());
    assertNull(controller.availableTimesComboBox.getValue());
    assertEquals(controller.yourMailText.getText(), controller.appManager.getUser().getEmail());
  }

  @Test
  void confirmBooking_notVisible_unlessAllSelected(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    assertNull(controller.availableTimesComboBox.getValue());
    assertFalse(controller.confirmBooking.isVisible());
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    assertTrue(controller.confirmBooking.isVisible());
  }

  @Test
  void when_availableTimesComboBox_isClicked(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    assertNull(controller.availableTimesComboBox.getValue());
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    assertEquals(LocalTime.of(9, 0), controller.availableTimesComboBox.getValue());
    assertEquals(controller.yourTimeText.getText(), "09:00");
  }

  @Test
  void test_confirm_Booking(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#availableTimesComboBox").clickOn("08:45");
    assertTrue(controller.confirmBooking.isVisible());
    robot.clickOn("#confirmBooking");
    assertEquals("Booking confirmed", controller.confirmedBookingLabel.getText());
  }

  @Test
  void test_updateAvailableTimes(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#availableTimesComboBox").clickOn("08:45");
    robot.clickOn("#confirmBooking");
    assertNull(controller.availableTimesComboBox.getValue());
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    assertTrue(controller.confirmedBookingLabel.getText().isEmpty());
  }

  @Test
  void test_Listeners(FxRobot robot) {
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    robot.clickOn("#availableTimesComboBox").clickOn("08:45");
    robot.clickOn("#courseComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    assertNull(controller.availableTimesComboBox.getValue());
    robot.clickOn("#availableTimesComboBox").clickOn("09:00");
    robot.clickOn("#dateComboBox");
    robot.type(KeyCode.DOWN);
    robot.type(KeyCode.ENTER);
    assertNull(controller.availableTimesComboBox.getValue());
  }
}
