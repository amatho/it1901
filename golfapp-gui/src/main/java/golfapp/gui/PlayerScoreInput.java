package golfapp.gui;

import golfapp.core.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PlayerScoreInput extends HBox {

  private final TextField scoreField;
  private int score = 3;

  public PlayerScoreInput(User user) {
    super();

    scoreField = new TextField("" + score);
    final var name = new TextField(user.getUsername());
    final var plus = new Button("+");
    final var minus = new Button("-");

    scoreField.setEditable(false);
    name.setEditable(false);
    plus.setOnMouseClicked(e -> handlePlus());
    minus.setOnMouseClicked(e -> handleMinus());

    getChildren().addAll(name, plus, scoreField, minus);
    getChildren().forEach(n -> setMargin(n, new Insets(5)));
  }

  private void handlePlus() {
    score++;
    scoreField.setText("" + score);
  }

  private void handleMinus() {
    if (score <= 1) {
      return;
    }

    score--;
    scoreField.setText("" + score);
  }
}
