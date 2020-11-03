package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class BookingController {

  private final AppManager appManager;
  private final Map<Course, BookingSystem> bookingSystems;

  @FXML
  ChoiceBox<LocalDate> dateChoiceBox;
  @FXML
  ChoiceBox<Course> courseChoiceBox;
  @FXML
  Button showAvailableTimes;
  @FXML
  Label outputLabel;
  @FXML
  ChoiceBox<LocalTime> availableTimesChoiceBox;
  @FXML
  Label yourBooking;
  @FXML
  Button confirmBooking;
  @FXML
  Label yourTimeLabel;
  @FXML
  Label yourDateLabel;
  @FXML
  Label yourCourseLabel;
  @FXML
  Label yourMailLabel;
  @FXML
  Label yourTimeText;
  @FXML
  Label yourCourseText;
  @FXML
  Label yourDateText;
  @FXML
  Label yourMailText;
  @FXML
  Label confirmedBookingLabel;

  /**
   * Create a {@code BookingController}.
   *
   * @param appManager the app manager
   */
  public BookingController(AppManager appManager) {
    this.appManager = appManager;
    this.bookingSystems = appManager.getModelDao().getBookingSystems();
  }

  @FXML
  void initialize() {
    showBooking(false);
    confirmedBookingLabel.setVisible(false);
    showCourse();
    showDate();
  }

  @FXML
  void showDate() {
    var dateChoiceBoxItems = dateChoiceBox.getItems();
    bookingSystems.values().stream().flatMap(BookingSystem::getAvailableDates).distinct()
        .forEach(dateChoiceBoxItems::add);
    dateChoiceBox.setItems(dateChoiceBoxItems);
    dateChoiceBox.getSelectionModel().selectFirst();
  }

  @FXML
  void showCourse() {
    courseChoiceBox.getItems().addAll(bookingSystems.keySet());
  }

  @FXML
  void showAvailableTimes() {
    availableTimesChoiceBox.getItems().clear();
    confirmedBookingLabel.setVisible(false);
    if (courseChoiceBox.getValue() == null) {
      outputLabel.setText("You must choose a course to see available times.");
    } else {
      availableTimesChoiceBox.setValue(null);
      yourTimeText.setText("");

      Course selectedCourse = courseChoiceBox.getValue();
      LocalDate selectedDate = dateChoiceBox.getValue();
      var availableTimesChoiceBoxItems = availableTimesChoiceBox.getItems();

      bookingSystems.get(selectedCourse).getAvailableTimes(selectedDate)
          .map(LocalDateTime::toLocalTime)
          .forEach(availableTimesChoiceBoxItems::add);

      outputLabel.setText("Choose an available time");
      showBooking(true);

      yourCourseText.setText(courseChoiceBox.getValue().getName());
      yourDateText.setText(dateChoiceBox.getValue().format(DateTimeFormatter.ISO_DATE));
      dateChoiceBox.getValue();
      yourMailText.setText(appManager.getUser().getEmail());

      availableTimesChoiceBox.getSelectionModel().selectedItemProperty()
          .addListener((availableTimesChoiceBox,
              oldValue, newValue) -> yourTimeText
              .setText(newValue.format(DateTimeFormatter.ISO_TIME)));
    }
  }

  @FXML
  void cleanBooking() {
    availableTimesChoiceBox.setValue(null);
    yourTimeText.setText("");
  }

  @FXML
  void confirmBooking() {
    confirmedBookingLabel.setVisible(true);
    confirmedBookingLabel.setText("");
    if (availableTimesChoiceBox.getValue() == null) {
      confirmedBookingLabel.setText("Your chosen time was not valid.");
    } else {
      confirmedBookingLabel.setText("Booking confirmed");

      Course selectedCourse = courseChoiceBox.getValue();
      LocalDateTime bookingTime = dateChoiceBox.getValue()
          .atTime(availableTimesChoiceBox.getValue());

      var bookingSystem = bookingSystems.get(selectedCourse);
      bookingSystem.addBooking(new Booking(appManager.getUser(), bookingTime));
      appManager.getModelDao().updateBookingSystem(selectedCourse, bookingSystem);

      cleanBooking();
      showBooking(false);
    }
  }

  @FXML
  void showBooking(Boolean b) {
    availableTimesChoiceBox.setVisible(b);
    yourBooking.setVisible(b);
    confirmBooking.setVisible(b);
    yourCourseLabel.setVisible(b);
    yourDateLabel.setVisible(b);
    yourTimeLabel.setVisible(b);
    yourMailLabel.setVisible(b);
    yourCourseText.setVisible(b);
    yourDateText.setVisible(b);
    yourMailText.setVisible(b);
    yourTimeText.setVisible(b);
  }

}
