package golfapp.gui;

import golfapp.core.Booking;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
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


  private final User user = new User("ama@example.com", "Amandus");
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
  TableView<Booking> bookedTimesTableView;
  @FXML
  TableColumn<Booking, String> bookedEmailColumn;
  @FXML
  TableColumn<Booking, String> bookedTimeColumn;

  @FXML
  void initialize() {
    username.setText("Name: " + user.getDisplayName());

    scorecardCourseColumn.setCellValueFactory(
        sc -> new ReadOnlyStringWrapper(sc.getValue().getCourse().getName()));
    scorecardTimeColumn
        .setCellValueFactory(sc -> new ReadOnlyStringWrapper(sc.getValue().getDate().toString()));

    bookedEmailColumn.setCellValueFactory(
        sc -> new ReadOnlyStringWrapper(sc.getValue().getUserEmail()));
    bookedTimeColumn
        .setCellValueFactory(
            sc -> new ReadOnlyStringWrapper(sc.getValue().getDateTime().toLocalDate().toString()));

    scorecardTableView.getSelectionModel().selectedItemProperty()
        .addListener((prop, oldValue, newValue) -> updateButton(
            scorecardTableView.getSelectionModel().getSelectedItem(), viewSelectedScorecard));
    bookedTimesTableView.getSelectionModel().selectedItemProperty()
        .addListener((prop, oldValue, newValue) -> updateButton(
            bookedTimesTableView.getSelectionModel().getSelectedItem(), cancelSelectedBooking));

    updateTableView(scorecardTableView, user.getScorecardHistory(), viewSelectedScorecard);
    // TODO: Get bookings from all the BookingSystems and find the bookings for the current user
    updateTableView(bookedTimesTableView, List.of(), cancelSelectedBooking);
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
    // TODO: See above
    // Booking toDelete = bookedTimesTableView.getSelectionModel().getSelectedItem();
    // user.removeBooking(toDelete);
    // updateTableView(bookedTimesTableView, user.getBookedTimes(), cancelSelectedBooking);
  }

  @FXML
  void handleLogOutButton(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader
        .load(getClass().getResource("LogIn.fxml")); //TODO: Implement LogIn.fxml
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }

  @FXML
  void handleViewSelectedScorecardButton(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader
        .load(getClass().getResource("ScorecardView.fxml")); //TODO: Implement ScorecardView.fxml
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }

  @FXML
  void handleAddBooking(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("Booking.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }
}
