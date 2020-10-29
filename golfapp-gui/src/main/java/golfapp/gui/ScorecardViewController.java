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
  private double prefWidth;

  @FXML
  VBox leftInfo;
  @FXML
  HBox holeIndex;
  @FXML
  HBox holeLength;
  @FXML
  HBox holePar;


  public ScorecardViewController(AppManager appManager, Scorecard scorecard) {
    this.appManager = appManager;
    this.scorecard = scorecard;
    this.course = scorecard.getCourse();
    this.holes = course.getHoles();
    prefWidth = course.getCourseLength() * 10;
  }

  @FXML
  void initialize() {
    double height = hBoxSetUp();
    leftInfo.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
    leftInfo.setPrefHeight(height);
    //leftInfo.setPrefWidth();
  }

  private double playerSetup() {
    double heightPlayers = 0.0;
    Set<String> users = scorecard.getUsers();
    for (String s : users) {
      HBox h = new HBox();
      h.getChildren().add(new Label(s));
      leftInfo.getChildren().add(h);
      h.setSpacing(10);
      h.setAlignment(Pos.CENTER_LEFT);
      h.setPrefHeight(80);
      h.setBorder(new Border(new BorderStroke(Color.BLACK,
          BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
      heightPlayers = heightPlayers +  80;
      h.setStyle("-fx-font: 24 arial;");
    }
    holePar = new HBox();
    holePar.setPrefHeight(80);
    leftInfo.getChildren().add(holePar);
    return heightPlayers;
  }

  private double hBoxSetUp() {
    int i = 1;
    holeLength.getChildren().add(new Label("Lengde:"));
    holeIndex.getChildren().add(new Label("Hull:"));
    double heightVbox = playerSetup();
    heightVbox += 3 * 80.0;
    holePar.getChildren().add(new Label("Par:"));

    for (Hole h : holes) {
      holeLength.getChildren().add(new Label(Double.toString(h.getLength())));
      holePar.getChildren().add(new Label(Integer.toString(h.getPar())));
      holeIndex.getChildren().add(new Label(Integer.toString(i)));
      i++;
    }
    holeIndex.setSpacing(10);
    holeLength.setSpacing(10);
    holePar.setSpacing(10);
    holeIndex.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holeLength.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holePar.setStyle("-fx-font: 24 arial; -fx-font-weight: bold");
    holeIndex.setAlignment(Pos.CENTER_LEFT);
    holeLength.setAlignment(Pos.CENTER_LEFT);
    holePar.setAlignment(Pos.CENTER_LEFT);
    holeIndex.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    holeLength.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    holePar.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

    return heightVbox;
  }
}