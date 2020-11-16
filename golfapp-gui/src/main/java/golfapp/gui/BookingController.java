package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.gui.cell.CourseCell;
import golfapp.gui.cell.LocalDateCell;
import golfapp.gui.cell.LocalTimeCell;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class BookingController {

  private final AppManager appManager;
  private final Map<Course, BookingSystem> bookingSystems;

  @FXML
  ComboBox<LocalDate> dateComboBox;
  @FXML
  ComboBox<Course> courseComboBox;
  @FXML
  Button showAvailableTimes;
  @FXML
  Label outputLabel;
  @FXML
  ComboBox<LocalTime> availableTimesComboBox;
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
    dateComboBox.setCellFactory(l -> new LocalDateCell());
    dateComboBox.setButtonCell(new LocalDateCell());
    courseComboBox.setCellFactory(l -> new CourseCell());
    courseComboBox.setButtonCell(new CourseCell());
    availableTimesComboBox.setCellFactory(l -> new LocalTimeCell());
    availableTimesComboBox.setButtonCell(new LocalTimeCell());

    showBooking(false);
    confirmedBookingLabel.setVisible(false);
    availableTimesComboBox.setVisible(false);
    showCourse();
    showDate();
  }

  @FXML
  void showDate() {
    var dateChoiceBoxItems = dateComboBox.getItems();
    bookingSystems.values().stream().flatMap(BookingSystem::getAvailableDates).distinct()
        .forEach(dateChoiceBoxItems::add);
    dateComboBox.setItems(dateChoiceBoxItems);
    dateComboBox.getSelectionModel().selectFirst();
  }

  @FXML
  void showCourse() {
    courseComboBox.getItems().addAll(bookingSystems.keySet());
  }

  @FXML
  void showAvailableTimes() {
    availableTimesComboBox.getItems().clear();
    confirmedBookingLabel.setVisible(false);
    showBooking(false);
    outputLabel.setVisible(true);

    if (courseComboBox.getValue() == null) {
      outputLabel.setText("You must choose a course to see available times.");
    } else {
      availableTimesComboBox.setValue(null);
      yourTimeText.setText("");

      Course selectedCourse = courseComboBox.getValue();
      LocalDate selectedDate = dateComboBox.getValue();
      var availableTimesComboBoxItems = availableTimesComboBox.getItems();

      bookingSystems.get(selectedCourse).getAvailableTimes(selectedDate)
          .map(LocalDateTime::toLocalTime)
          .forEach(availableTimesComboBoxItems::add);

      outputLabel.setText("Choose a time:");
      availableTimesComboBox.setVisible(true);

      yourCourseText.setText(courseComboBox.getValue().getName());
      yourDateText.setText(dateComboBox.getValue().format(DateTimeFormatter.ISO_DATE));
      dateComboBox.getValue();
      yourMailText.setText(appManager.getUser().getEmail());
      availableTimesComboBox.getSelectionModel().selectedItemProperty()
          .addListener((availableTimesComboBox,
              oldValue, newValue) -> {
            if (newValue == null) {
              yourTimeText.setText("");
            } else {
              yourTimeText
                  .setText(newValue.format(DateTimeFormatter.ofPattern("HH:mm")));
              showBooking(true);

            }
          });
    }
  }

  @FXML
  void cleanBooking() {
    availableTimesComboBox.setValue(null);
    yourTimeText.setText("");
  }

  @FXML
  void confirmBooking() {
    confirmedBookingLabel.setVisible(true);
    confirmedBookingLabel.setText("");
    if (availableTimesComboBox.getValue() == null) {
      confirmedBookingLabel.setText("Your chosen time was not valid.");
    } else {
      confirmedBookingLabel.setText("Booking confirmed");

      Course selectedCourse = courseComboBox.getValue();
      LocalDateTime bookingTime = dateComboBox.getValue()
          .atTime(availableTimesComboBox.getValue());

      var bookingSystem = bookingSystems.get(selectedCourse);
      bookingSystem.addBooking(new Booking(appManager.getUser(), bookingTime));
      appManager.getModelDao().updateBookingSystem(selectedCourse, bookingSystem);

      cleanBooking();
      showBooking(false);
      availableTimesComboBox.setVisible(false);
      outputLabel.setVisible(false);
    }
  }

  @FXML
  void showBooking(Boolean b) {
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
