package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.data.DaoFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class BookingController {

  private final AppManager appManager;
  private final List<BookingSystem> bookingSystems;
  private final List<Course> courses;

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

  public BookingController(AppManager appManager) {
    this.appManager = appManager;
    bookingSystems = appManager.getBookingSystems();

    courses = DaoFactory.courseDao().getAllIgnoreId().collect(Collectors.toList());
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
    bookingSystems.stream().flatMap(BookingSystem::getAvailableDates).distinct()
        .forEach(dateChoiceBoxItems::add);
    dateChoiceBox.setItems(dateChoiceBoxItems);
    dateChoiceBox.getSelectionModel().selectFirst();
  }

  @FXML
  void showCourse() {
    courseChoiceBox.getItems().addAll(courses);
  }

  @FXML
  void showAvailableTimes() {
    availableTimesChoiceBox.getItems().clear();
    confirmedBookingLabel.setVisible(false);
    if (courseChoiceBox.getValue() == null) {
      outputLabel.setText("Du må velge en bane for å se ledige tider.");
    } else {
      availableTimesChoiceBox.setValue(null);
      yourTimeText.setText("");

      Course selectedCourse = courseChoiceBox.getValue();
      LocalDate selectedDate = dateChoiceBox.getValue();
      var availableTimesChoiceBoxItems = availableTimesChoiceBox.getItems();
      bookingSystems.stream().filter(b -> b.getCourse().equals(selectedCourse)).findAny()
          .orElseThrow().getAvailableTimes(selectedDate)
          .map(LocalDateTime::toLocalTime)
          .forEach(availableTimesChoiceBoxItems::add);

      outputLabel.setText("Velg en ledig tid");
      showBooking(true);

      yourCourseText.setText(courseChoiceBox.getValue().toString());
      yourDateText.setText(String.valueOf(dateChoiceBox.getValue()));
      dateChoiceBox.getValue();
      yourMailText.setText(appManager.getUser().getEmail());

      availableTimesChoiceBox.getSelectionModel().selectedItemProperty()
          .addListener((availableTimesChoiceBox,
              oldValue, newValue) -> yourTimeText.setText(String.valueOf(newValue)));
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
      confirmedBookingLabel.setText("Du har ikke valgt et gyldig tidspunkt.");
    } else {
      confirmedBookingLabel.setText("Booking bekreftet");

      Course selectedCourse = courseChoiceBox.getValue();
      LocalDateTime bookingTime = dateChoiceBox.getValue()
          .atTime(availableTimesChoiceBox.getValue());
      bookingSystems.stream().filter(b -> b.getCourse().equals(selectedCourse)).findAny()
          .orElseThrow().addBooking(new Booking(yourMailText.getText(), bookingTime));

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
  /*@FXML
  void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("Booking.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }
   */
}
