package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BookingController {

  private final BookingSystem bookingSystem = new BookingSystem();
  private final List<Course> courses = new ArrayList<>();
  private final User user1 = new User("ola.nordmann@gmail.com", "OlaN");

  @FXML
  private ChoiceBox<LocalDate> dateChoiceBox;
  @FXML
  private ChoiceBox<String> courseChoiceBox;
  @FXML
  private Button showAvailableTimes;
  @FXML
  private Label outputLabel;
  @FXML
  private ChoiceBox<LocalTime> availableTimesChoiceBox;
  @FXML
  private Label mailLabel;
  @FXML
  private TextField mail;
  @FXML
  private Label yourBooking;
  @FXML
  private Button confirmBooking;
  @FXML
  private Label yourTimeLabel;
  @FXML
  private Label yourDateLabel;
  @FXML
  private Label yourCourseLabel;
  @FXML
  private Label yourMailLabel;
  @FXML
  private Label yourTimeText;
  @FXML
  private Label yourCourseText;
  @FXML
  private Label yourDateText;
  @FXML
  private Label yourMailText;
  @FXML
  private Label confirmedBookingLabel;


  @FXML
  void initialize() {
    //getAllAvailableTimes = booking.getAllAvailableTimes();
    //getAllBookedTimes = booking.getAllBookedTimes();
    showBooking(false);
    confirmedBookingLabel.setVisible(false);
    showCourse();
    showDate();
  }

  @FXML
  void showDate() {
    List<LocalDate> availableDates = bookingSystem.getAvailableDates()
        .collect(Collectors.toList());
    dateChoiceBox.getItems().addAll(availableDates);
    dateChoiceBox.setValue(availableDates.get(0));

  }

  @FXML
  void showCourse() {
    courses.add(new Course("Oslo Golfklubb", List.of(), new BookingSystem()));
    courses.add(new Course("Trondheim GK", List.of(), new BookingSystem()));
    courses.add(new Course("Bærum Golfklubb", List.of(), new BookingSystem()));
    courses.add(new Course("Alta GK", List.of(), new BookingSystem()));
    List<String> courseNames = new ArrayList<>();
    for (Course c : courses) {
      courseNames.add(c.getName());
    }
    courseChoiceBox.getItems().addAll(courseNames);
  }

  @FXML
  void showAvailableTimes() {
    confirmedBookingLabel.setVisible(false);
    if (courseChoiceBox.getValue() == null) {
      outputLabel.setText("Du må velge en bane for å se ledige tider.");
    } else {
      availableTimesChoiceBox.setValue(null);
      yourTimeText.setText("");
      Course onCourse = courses.get(courseChoiceBox.getItems()
          .indexOf(courseChoiceBox.getValue()));
      LocalDate d = dateChoiceBox.getValue();
      List<LocalTime> availableTimes = onCourse
          .getBookingSystem().getAvailableTimes(d)
          .map(LocalDateTime::toLocalTime)
          .collect(Collectors.toList());
      availableTimesChoiceBox.getItems().addAll(availableTimes);
      outputLabel.setText("Velg en ledig tid");
      showBooking(true);
      yourCourseText.setText(courseChoiceBox.getValue());
      yourDateText.setText(String.valueOf(dateChoiceBox.getValue()));
      dateChoiceBox.getValue();
      mail.textProperty().addListener((mail, oldText, newText)  -> {
        yourMailText.setText("");
        yourMailText.setText(newText);
      });
      availableTimesChoiceBox.getSelectionModel().selectedItemProperty()
          .addListener((availableTimesChoiceBox,
          oldValue, newValue) -> yourTimeText.setText(String.valueOf(newValue)));

    }
  }

  @FXML
  void cleanBooking() {
    showCourse();
    showDate();
    availableTimesChoiceBox.setValue(null);
    yourMailText.setText("");
    mail.setText("");
    yourTimeText.setText("");
  }

  @FXML
  void confirmBooking() {
    confirmedBookingLabel.setVisible(true);
    confirmedBookingLabel.setText("");
    if (!user1.getEmail().equals(yourMailText.getText())) {
      confirmedBookingLabel.setText("Mailen din stemmer ikke med brukeren sin.");
    } else if (availableTimesChoiceBox.getValue() == null) {
      confirmedBookingLabel.setText("Du har ikke valgt et gyldig tidspunkt.");
    } else {
      confirmedBookingLabel.setText("Booking bekreftet");
      Course onCourse = courses.get(courseChoiceBox.getItems()
          .indexOf(courseChoiceBox.getValue()));
      LocalDateTime bookingTime = dateChoiceBox.getValue()
          .atTime(availableTimesChoiceBox.getValue());
      onCourse.getBookingSystem().addBooking(new Booking(yourMailText.getText(), bookingTime));
      cleanBooking();
      showBooking(false);

    }

  }

  @FXML
  void showBooking(Boolean b) {
    availableTimesChoiceBox.setVisible(b);
    mail.setVisible(b);
    mailLabel.setVisible(b);
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
