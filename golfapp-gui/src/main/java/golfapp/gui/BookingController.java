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

  final AppManager appManager;
  private final Map<Course, BookingSystem> bookingSystems;

  @FXML
  ComboBox<LocalDate> dateComboBox;
  @FXML
  ComboBox<Course> courseComboBox;
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

    availableTimesComboBox.getSelectionModel().selectedItemProperty()
        .addListener((availableTimesComboBox,
            oldValue, newValue) -> {
          if (newValue == null) {
            yourTimeText.setText("");
            confirmBooking.setVisible(false);
          } else {
            yourTimeText
                .setText(newValue.format(DateTimeFormatter.ofPattern("HH:mm")));
            confirmedBookingLabel.setText("");
            confirmBooking.setVisible(true);
          }
        });

    dateComboBox.getSelectionModel().selectedItemProperty()
        .addListener((dateComboBox,
            oldValue, newValue) -> {
          if (newValue == null) {
            yourDateText.setText("");
          } else {
            yourDateText
                .setText(newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            updateAvailableTimes();
          }
        });

    courseComboBox.getSelectionModel().selectedItemProperty()
        .addListener((courseComboBox,
            oldValue, newValue) -> {
          if (newValue == null) {
            yourCourseText.setText("");
          } else {
            yourCourseText.setText(newValue.getName());
            updateAvailableTimes();
          }
        });

    showBooking(true);
    confirmedBookingLabel.setVisible(false);
    confirmBooking.setVisible(false);
    availableTimesComboBox.setVisible(true);
    outputLabel.setVisible(true);
    updateCourses();
    yourMailText.setText(appManager.getUser().getEmail());
    updateDates();
  }

  void updateDates() {
    var dateChoiceBoxItems = dateComboBox.getItems();
    bookingSystems.values().stream().flatMap(BookingSystem::getAvailableDates).distinct()
        .forEach(dateChoiceBoxItems::add);
    dateComboBox.setItems(dateChoiceBoxItems);
    dateComboBox.getSelectionModel().selectFirst();
  }

  void updateCourses() {
    courseComboBox.getItems().addAll(bookingSystems.keySet());
  }

  void updateAvailableTimes() {
    Course selectedCourse = courseComboBox.getValue();
    LocalDate selectedDate = dateComboBox.getValue();

    if (selectedCourse == null || selectedDate == null) {
      availableTimesComboBox.setVisible(false);
      outputLabel.setVisible(false);
      return;
    }

    availableTimesComboBox.setVisible(true);
    outputLabel.setVisible(true);
    var availableTimesComboBoxItems = availableTimesComboBox.getItems();

    availableTimesComboBoxItems.clear();
    bookingSystems.get(selectedCourse).getAvailableTimes(selectedDate)
        .map(LocalDateTime::toLocalTime)
        .forEach(availableTimesComboBoxItems::add);

    yourCourseText.setText(courseComboBox.getValue().getName());
    yourDateText.setText(dateComboBox.getValue().format(DateTimeFormatter.ISO_DATE));
  }

  @FXML
  void confirmBooking() {
    confirmedBookingLabel.setVisible(true);
    confirmedBookingLabel.setText("Booking confirmed");

    Course selectedCourse = courseComboBox.getValue();
    LocalDateTime bookingTime = dateComboBox.getValue()
        .atTime(availableTimesComboBox.getValue());

    var bookingSystem = bookingSystems.get(selectedCourse);
    bookingSystem.addBooking(new Booking(appManager.getUser(), bookingTime));
    appManager.getModelDao().updateBookingSystem(selectedCourse, bookingSystem);
    updateAvailableTimes();
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
