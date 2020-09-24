package gui;

import core.User;
import java.io.IOException;
import java.util.NoSuchElementException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ScoreCardController {

  @FXML
  Button addButton, createButton, deleteButton;

  @FXML
  TableView<User> tableView;

  @FXML
  TableColumn<User, String> usernameColumn, userIDColumn;

  @FXML
  TextField usernameField;


  @FXML
  public void initialize() {
    usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
    userIDColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userID"));
  }

  @FXML
  public void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent CourseParent = FXMLLoader.load(getClass().getResource("Course.fxml"));
    Scene CourseScene = new Scene(CourseParent);
    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    window.setScene(CourseScene);
    window.show();
  }

  @FXML
  public void newUserButtonPushed() {
    User User = new User(usernameField.getText());
    tableView.getItems().add(User);
  }

  @FXML
  public void deleteUserButtonPushed() {
    ObservableList<User> Users = tableView.getItems();
    var selectedUser = tableView.getSelectionModel().getSelectedItem();

    try{
        Users.remove(selectedUser);
      } catch (NoSuchElementException e) {
    }
  }

}
