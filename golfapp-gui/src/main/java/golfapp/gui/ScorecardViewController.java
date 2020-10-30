package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ScorecardViewController {

  private final AppManager appManager;
  private final Scorecard scorecard;
  private final Course course;
  private final List<Hole> holes;
  private double vboxPrefWidth = 0;
  private final double widthLeftLabel = 120;

  @FXML
  VBox leftInfo;
  @FXML
  HBox holeIndex;
  @FXML
  HBox holeLength;
  @FXML
  HBox holePar;
  @FXML
  Label infoLabel;

  /**
   * Create a controller for ScorecardViewController.
   *
   * @param appManager takes in appManager
   * @param scorecard  takes in the relevant scorecard
   */
  public ScorecardViewController(AppManager appManager, Scorecard scorecard) {
    this.appManager = appManager;
    this.scorecard = scorecard;
    this.course = scorecard.getCourse();
    this.holes = course.getHoles();
  }

  @FXML
  void initialize() {
    double height = horizontalBoxSetup();
    leftInfo.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    leftInfo.setPrefHeight(height);
    leftInfo.setPrefWidth(vboxPrefWidth);
    infoLabel.setText("Name: " + course.getName()
        + "         Date: " + scorecard.getDate());
  }

  @FXML
  void handleGoToUser() {
    appManager.loadView("User.fxml", UserController::new);
  }

  private double playerSetup() {
    double heightPlayers = 0.0;
    Set<String> users = scorecard.getUsers();
    for (String s : users) {
      Label label = new Label(s);
      label.setBorder(new Border(new BorderStroke(Color.BLACK,
          BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
      label.setPrefWidth(widthLeftLabel);
      label.setAlignment(Pos.CENTER);
      HBox h = new HBox();
      h.getChildren().add(label);

      //method for now invalid
      /*for (Hole hole : holes) {
        int score = scorecard.getScore(s, hole); // invalid type for now.
        Label scoreEachHole = new Label(Integer.toString(score));
        scoreEachHole.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        scoreEachHole.setPrefWidth(widthLeftLabel);
        scoreEachHole.setAlignment(Pos.CENTER);
        h.getChildren().add(scoreEachHole);
      }*/

      leftInfo.getChildren().add(h);
      h.setPrefHeight(80);
      heightPlayers = heightPlayers + h.getHeight();
      h.setStyle("-fx-font: 24 arial;");
    }

    holePar = new HBox();
    holePar.setPrefHeight(60);
    heightPlayers += holePar.getHeight();
    leftInfo.getChildren().add(holePar);
    return heightPlayers;
  }

  private double horizontalBoxSetup() {
    int i = 1;
    Label holeLengthLabel = new Label("Length:");
    holeLengthLabel.setPrefWidth(widthLeftLabel);
    holeLengthLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    holeLengthLabel.setAlignment(Pos.CENTER);
    holeLength.getChildren().add(holeLengthLabel);

    Label holeIndexLabel = new Label("Hole:");
    holeIndexLabel.setPrefWidth(widthLeftLabel);
    holeIndexLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    holeIndexLabel.setAlignment(Pos.CENTER);
    holeIndex.getChildren().add(holeIndexLabel);

    vboxPrefWidth += holeLengthLabel.getPrefWidth();
    double heightVbox = playerSetup();
    heightVbox += holeIndex.getHeight() + holeLength.getHeight();

    Label holeParLabel = new Label("Par:");
    holeParLabel.setPrefWidth(widthLeftLabel);
    holeParLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    holeParLabel.setAlignment(Pos.CENTER);
    holePar.getChildren().add(holeParLabel);

    for (Hole h : holes) {
      Label label1 = new Label(Double.toString(h.getLength()));
      Label label2 = new Label(Integer.toString(h.getPar()));
      Label label3 = new Label(Integer.toString(i));

      label1.setBorder(new Border(new BorderStroke(Color.BLACK,
          BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
      label2.setBorder(new Border(new BorderStroke(Color.BLACK,
          BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
      label3.setBorder(new Border(new BorderStroke(Color.BLACK,
          BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

      double widthOtherLabels = 80;
      label1.setPrefWidth(widthOtherLabels);
      label2.setPrefWidth(widthOtherLabels);
      label3.setPrefWidth(widthOtherLabels);

      label1.setAlignment(Pos.CENTER);
      label2.setAlignment(Pos.CENTER);
      label3.setAlignment(Pos.CENTER);

      holeLength.getChildren().add(label1);
      holePar.getChildren().add(label2);
      holeIndex.getChildren().add(label3);

      vboxPrefWidth += label1.getPrefWidth() + 2; // +borderWidth
      i++;
    }

    holeIndex.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holeLength.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holePar.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");

    return heightVbox;
  }
}