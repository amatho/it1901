package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
  @FXML
  ScrollPane scrollPane;
  @FXML
  Pane pane;

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
    horizontalBoxSetup();
    leftInfo.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    leftInfo.setPrefHeight(scrollPane.getPrefHeight());
    leftInfo.setPrefWidth(vboxPrefWidth);
    infoLabel.setText("Name: " + course.getName()
        + "         Date: " + scorecard.getDate());
    scrollPane.setContent(leftInfo);
    scrollPane.setFitToHeight(true);
  }

  @FXML
  void handleGoToUser() {
    appManager.loadView("User.fxml", UserController::new);
  }

  /**
   * Sets up the HBox for each user and for the par-HBox. Fills each HBox with labels. Fills each
   * label with the respective score or the par for the hole(par-HBox). Adds each HBox to leftInfo.
   */
  private void playerSetup() {
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
      h.setPrefHeight(60);
      leftInfo.getChildren().add(h);
    }

    holePar = new HBox();
    holePar.setPrefHeight(60);
    leftInfo.getChildren().add(holePar);
  }

  /**
   * Sets up all the HBoxes in order. Fills in HBox holeLength, holePar and holeIndex.
   */
  private void horizontalBoxSetup() {
    int i = 1;
    Label holeLengthLabel = new Label("Length:");
    styleLabel(holeLengthLabel, widthLeftLabel);
    holeLength.getChildren().add(holeLengthLabel);

    Label holeIndexLabel = new Label("Hole:");
    styleLabel(holeIndexLabel, widthLeftLabel);
    holeIndex.getChildren().add(holeIndexLabel);

    vboxPrefWidth += widthLeftLabel;
    holeIndex.setPrefHeight(60);
    holeLength.setPrefHeight(60);
    playerSetup();

    Label holeParLabel = new Label("Par:");
    styleLabel(holeParLabel, widthLeftLabel);
    holePar.getChildren().add(holeParLabel);

    for (Hole h : holes) {
      Label length = new Label(Double.toString(h.getLength()));
      Label par = new Label(Integer.toString(h.getPar()));
      Label index = new Label(Integer.toString(i));

      styleLabel(length, widthOtherLabels);
      styleLabel(par, widthOtherLabels);
      styleLabel(index, widthOtherLabels);

      holeLength.getChildren().add(length);
      holePar.getChildren().add(par);
      holeIndex.getChildren().add(index);

      vboxPrefWidth += widthOtherLabels;
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
  }

  /**
   * Styles the label.
   *
   * @param l     the label to be styled.
   * @param width the preferred width of l.
   */
  private void styleLabel(Label l, Double width) {
    l.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    l.setPrefWidth(width);
    l.setAlignment(Pos.CENTER);
    int i = scorecard.getUsers().size() + 3;
    l.setPrefHeight(scrollPane.getPrefHeight() / i);
  }
}