package golfapp.gui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
public class UserControllerTest extends AbstractControllerTest<UserController> {

  @Start
  void start(final Stage stage) throws IOException {
    loadFxml(stage);
  }

  @Override
  String fxmlName() {
    return "User.fxml";
  }

  @Override
  UserController controllerFactory() {
    User user = new User("foo@test.com", "Foo Bar");
    Hole h1 = new Hole(1.0, 3, 1.2);
    Hole h2 = new Hole(1.0, 3, 1.2);
    LocalDateTime ldt1 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 30));
    LocalDateTime ldt2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 45));
    Booking booking1 = new Booking(user, ldt1);
    Booking booking2 = new Booking(user, ldt2);
    Course c1 = new Course("Course-1", Arrays.asList(h1, h2));
    Course c2 = new Course("Course-2", Arrays.asList(h1, h2));
    BookingSystem bookingSystemC1 = new BookingSystem();
    BookingSystem bookingSystemC2 = new BookingSystem();
    bookingSystemC1.addBooking(booking1);
    bookingSystemC2.addBooking(booking2);
    Scorecard s1 = new Scorecard(c1, List.of(user));
    Scorecard s2 = new Scorecard(c2, List.of(user));
    user.addScorecard(s1);
    user.addScorecard(s2);

    var appManager = mock(AppManager.class);
    var modelDao = mock(GolfAppModelDao.class);
    when(modelDao.getBookingSystems()).thenReturn(Map.of(c1, bookingSystemC1, c2, bookingSystemC2));
    when(appManager.getModelDao()).thenReturn(modelDao);
    when(appManager.getUser()).thenReturn(user);

    return new UserController(appManager);
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
