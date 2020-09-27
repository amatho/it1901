package golfapp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

public class CourseController {

  @FXML
  ToolBar toolbar;

  @FXML
  Button plus, minus;

  @FXML
  TextField textField, playerName;

  @FXML
  void initialize() {
    textField.setText("3");
    playerName.setText("Player 1");
  }

  @FXML
  void handleClick() {
    var value = Integer.parseInt(textField.getText());

    if (value > 0) {
      if (plus.isFocused()) {
        textField.setText(Integer.toString(value + 1));
      } else if (minus.isFocused() && value > 1) {
        textField.setText(Integer.toString(value - 1));
      }
    }

  }
}
