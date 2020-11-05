package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserController {

  private static final class BookingTableEntry {

    private Course course;
    private Booking booking;
  }

  private final AppManager appManager;
  private final User user;

  @FXML
  Label username;
  @FXML
  Button viewSelectedScorecard;
  @FXML
  Button logOut;
  @FXML
  Button cancelSelectedBooking;
  @FXML
  Button addBooking;
  @FXML
  TableView<Scorecard> scorecardTableView;
  @FXML
  TableColumn<Scorecard, String> scorecardCourseColumn;
  @FXML
  TableColumn<Scorecard, String> scorecardTimeColumn;
  @FXML
  TableView<BookingTableEntry> bookedTimesTableView;
  @FXML
  TableColumn<BookingTableEntry, String> bookedCourseColumn;
  @FXML
  TableColumn<BookingTableEntry, String> bookedTimeColumn;

  public UserController(AppManager appManager) {
    this.appManager = appManager;
    user = appManager.getUser();
  }

  public User getUser() {
    return user;
  }

  @FXML
  void initialize() {
    username.setText("Name: " + user.getDisplayName());

    scorecardCourseColumn.setCellValueFactory(
        sc -> new ReadOnlyStringWrapper(sc.getValue().getCourse().getName()));
    scorecardTimeColumn
        .setCellValueFactory(sc -> new ReadOnlyStringWrapper(sc.getValue().getDate().toString()));

    bookedCourseColumn.setCellValueFactory(
        sc -> new ReadOnlyStringWrapper(sc.getValue().course.getName()));
    bookedTimeColumn
        .setCellValueFactory(
            sc -> new ReadOnlyStringWrapper(
                sc.getValue().booking.getDateTime()
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))));

    scorecardTableView.getSelectionModel().selectedItemProperty()
        .addListener((prop, oldValue, newValue) -> updateButton(
            scorecardTableView.getSelectionModel().getSelectedItem(), viewSelectedScorecard));
    bookedTimesTableView.getSelectionModel().selectedItemProperty()
        .addListener((prop, oldValue, newValue) -> updateButton(
            bookedTimesTableView.getSelectionModel().getSelectedItem(), cancelSelectedBooking));

    updateTableView(scorecardTableView, user.getScorecardHistory(), viewSelectedScorecard);
    updateBookings();
  }

  private void updateBookings() {
    var bookings = appManager.getModelDao().getBookingSystems().entrySet().stream()
        .flatMap(e -> e.getValue().getBookings().stream()
            .filter(b -> b.getUser().equals(user))
            .map(b -> {
              BookingTableEntry tableEntry = new BookingTableEntry();
              tableEntry.course = e.getKey();
              tableEntry.booking = b;
              return tableEntry;
            })).collect(Collectors.toList());

    updateTableView(bookedTimesTableView, bookings, cancelSelectedBooking);
  }

  private <T> void updateTableView(TableView<T> tableView, Collection<T> collection,
      Button button) {
    tableView.getItems().clear();
    tableView.getItems().addAll(collection);
    updateButton(tableView.getSelectionModel().getSelectedItem(), button);
  }

  @FXML
  private void updateButton(Object object, Button button) {
    boolean disable = object == null;
    button.setDisable(disable);
  }

  @FXML
  void handleCancelSelectedBooking() {
    BookingTableEntry entry = bookedTimesTableView.getSelectionModel().getSelectedItem();
    Course course = entry.course;
    Booking toDelete = entry.booking;

    BookingSystem bookingSystem = appManager.getModelDao().getBookingSystems().get(course);
    bookingSystem.removeBooking(toDelete);
    appManager.getModelDao().updateBookingSystem(course, bookingSystem);

    updateBookings();
  }

  @FXML
  void handleLogOutButton(ActionEvent event) throws IOException {
    Parent parent = FXMLLoader
        .load(getClass().getResource("LogIn.fxml"));
    Scene scene = new Scene(parent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(scene);
    window.show();
  }

  @FXML
  void handleViewSelectedScorecardButton() {
    appManager.loadView("ScorecardView.fxml",
        a -> new ScorecardViewController(a, scorecardTableView.getSelectionModel()
            .getSelectedItem()));
  }

  @FXML
  void handleAddBooking() {
    appManager.loadView("Booking.fxml", BookingController::new);
  }
}
