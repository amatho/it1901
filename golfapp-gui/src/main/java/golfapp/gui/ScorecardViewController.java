package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
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
  private static final double widthLeftLabel = 150;
  private static final double widthOtherLabels = 80;

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
    if (vboxPrefWidth > 700) {
      vboxPrefWidth = 700;
    }
    leftInfo.setPrefWidth(vboxPrefWidth);
    infoLabel.setText("Name: " + course.getName()
        + "         Date: " + scorecard.getDate());
    ScrollBar sc = new ScrollBar();
    sc.setMin(0);
    sc.setOrientation(Orientation.HORIZONTAL);
    leftInfo.getChildren().add(sc);
  }

  @FXML
  void handleGoToUser() {
    appManager.loadView("User.fxml", UserController::new);
  }

  private double playerSetup() {
    double heightPlayers = 0.0;
    Set<User> users = scorecard.getUsers();
    for (User s : users) {
      Label label = new Label(s.getDisplayName());
      styleLabel(label, widthLeftLabel);
      HBox h = new HBox();
      h.getChildren().add(label);

      for (Hole hole : holes) {
        int score = scorecard.getScore(s, hole);
        Label scoreEachHole = new Label(Integer.toString(score));
        styleLabel(scoreEachHole, widthOtherLabels);
        h.getChildren().add(scoreEachHole);
      }
      Label totalPlayerScore = new Label(Integer.toString(scorecard.getTotalScore(s)));
      styleLabel(totalPlayerScore, widthOtherLabels);
      h.getChildren().add(totalPlayerScore);

      h.setStyle("-fx-font: 24 arial;");
      h.setPrefHeight(80);
      leftInfo.getChildren().add(h);
      heightPlayers = heightPlayers + h.getHeight();
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
    styleLabel(holeLengthLabel, widthLeftLabel);
    holeLength.getChildren().add(holeLengthLabel);

    Label holeIndexLabel = new Label("Hole:");
    styleLabel(holeIndexLabel, widthLeftLabel);
    holeIndex.getChildren().add(holeIndexLabel);

    vboxPrefWidth += widthLeftLabel;
    double heightVbox = playerSetup();
    heightVbox += holeIndex.getHeight() + holeLength.getHeight();

    Label holeParLabel = new Label("Par:");
    styleLabel(holeParLabel, widthLeftLabel);
    holePar.getChildren().add(holeParLabel);

    for (Hole h : holes) {
      Label label1 = new Label(Double.toString(h.getLength()));
      Label label2 = new Label(Integer.toString(h.getPar()));
      Label label3 = new Label(Integer.toString(i));

      styleLabel(label1, widthOtherLabels);
      styleLabel(label2, widthOtherLabels);
      styleLabel(label3, widthOtherLabels);

      holeLength.getChildren().add(label1);
      holePar.getChildren().add(label2);
      holeIndex.getChildren().add(label3);

      vboxPrefWidth += widthOtherLabels + 2; // +borderWidth
      i++;
    }

    Label total = new Label("Total:");
    styleLabel(total, widthOtherLabels);
    holeIndex.getChildren().add(total);

    double totLength = 0;
    int totPar = 0;
    for (Hole h : holes) {
      totLength += h.getLength();
      totPar += h.getPar();
    }
    Label totalLength = new Label(Double.toString(totLength));
    styleLabel(totalLength, widthOtherLabels);
    holeLength.getChildren().add(totalLength);

    Label totalPar = new Label(Integer.toString(totPar));
    styleLabel(totalPar, widthOtherLabels);
    holePar.getChildren().add(totalPar);

    vboxPrefWidth += widthOtherLabels;

    holeIndex.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holeLength.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holePar.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");

    return heightVbox;
  }

  private void styleLabel(Label l, Double width) {
    l.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    l.setPrefWidth(width);
    l.setAlignment(Pos.CENTER);

  }
}