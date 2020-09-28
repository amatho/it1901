package golfapp.gui;

import golfapp.core.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class CourseController {

  private final ArrayList<Button> plusButtons = new ArrayList<>();
  private final ArrayList<Button> minusButtons = new ArrayList<>();
  private final ObservableList<TextField> playerScores = FXCollections.observableArrayList();
  private final ObservableList<TextField> playerNames = FXCollections.observableArrayList();

  @FXML
  ToolBar toolbar;
  @FXML
  ToolBar toolbar1;
  @FXML
  ToolBar toolbar2;
  @FXML
  ToolBar toolbar3;
  @FXML
  Button plus;
  @FXML
  Button plus1;
  @FXML
  Button plus2;
  @FXML
  Button plus3;
  @FXML
  Button minus;
  @FXML
  Button minus1;
  @FXML
  Button minus2;
  @FXML
  Button minus3;
  @FXML
  TextField textField;
  @FXML
  TextField textField1;
  @FXML
  TextField textField2;
  @FXML
  TextField textField3;
  @FXML
  TextField playerName;
  @FXML
  TextField playerName1;
  @FXML
  TextField playerName2;
  @FXML
  TextField playerName3;

  @FXML
  void initialize() {
    plusButtons.addAll(Arrays.asList(plus, plus1, plus2, plus3));
    minusButtons.addAll(Arrays.asList(minus, minus1, minus2, minus3));
    playerScores.addAll(textField, textField1, textField2, textField3);
    playerNames.addAll(playerName, playerName1, playerName2, playerName3);
  }

  @FXML
  void initData(ObservableList<User> users) {
    for (var user : users) {
      playerNames.stream()
                 .filter(u -> u.getText().isEmpty())
                 .findFirst()
                 .ifPresent(u -> u.setText(user.getUsername()));
    }
  }

  @FXML
  void handlePlusPressed(ActionEvent event) {
    var button = (Button) event.getSource();
    var textFieldValue = playerScores.get(plusButtons.indexOf(button));
    var textFieldName = playerNames.get(plusButtons.indexOf(button));
    var value = Integer.parseInt(textFieldValue.getText());

    if (value < 100 && !textFieldName.getText().isEmpty()) {
      textFieldValue.setText(Integer.toString(value + 1));
    }
  }

  @FXML
  void handleMinusPressed(ActionEvent event) {
    var button = (Button) event.getSource();
    var textFieldValue = playerScores.get(minusButtons.indexOf(button));
    var textFieldName = playerNames.get(minusButtons.indexOf(button));
    var value = Integer.parseInt(textFieldValue.getText());

    if (value > 1 && !textFieldName.getText().isEmpty()) {
      textFieldValue.setText(Integer.toString(value - 1));
    }
  }

  @FXML
  void changeSceneButtonPushed(ActionEvent event) throws IOException {
    Parent courseParent = FXMLLoader.load(getClass().getResource("ScoreCard.fxml"));
    Scene courseScene = new Scene(courseParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(courseScene);
    window.show();
  }
}
