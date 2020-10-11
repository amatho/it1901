package golfapp.gui;

import golfapp.core.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PlayerScoreInput extends HBox {

  private final TextField scoreField;
  private int score = 3;

  /**
   * Creates a {@code HBox} containing text fields for score and name, and buttons for modifying the
   * score.
   *
   * @param user the user to get the name from
   */
  public PlayerScoreInput(User user) {
    super();

    scoreField = new TextField("" + score);
    final var name = new TextField(user.getDisplayName());
    final var plus = new Button("+");
    final var minus = new Button("-");

    scoreField.setEditable(false);
    name.setEditable(false);
    minus.setOnMouseClicked(e -> handleMinus());
    plus.setOnMouseClicked(e -> handlePlus());

    getChildren().addAll(name, minus, scoreField, plus);
    getChildren().forEach(n -> setMargin(n, new Insets(5)));
  }

  private void handleMinus() {
    if (score <= 1) {
      return;
    }

    score--;
    scoreField.setText("" + score);
  }

  private void handlePlus() {
    score++;
    scoreField.setText("" + score);
  }
}
