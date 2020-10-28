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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ScorecardViewController {
  private final AppManager appManager;
  private final Scorecard scorecard;
  private Course course;
  private List<Hole> holes;

  @FXML
  VBox leftInfo;
  @FXML
  HBox holeIndex;
  @FXML
  HBox holeLength;
  @FXML
  HBox holePar;
  @FXML
  HBox playersScore;

  public ScorecardViewController(AppManager appManager, Scorecard scorecard) {
    this.appManager = appManager;
    this.scorecard = scorecard;
    this.course = scorecard.getCourse();
    this.holes = course.getHoles();
  }

  @FXML
  void initialize() {
    HBoxSetUp();
    //leftInfoSet();

  }
  private void playerSetup() {
    Set<String> users = scorecard.getUsers();
    for (String s : users) {
      HBox h = new HBox();
      h.getChildren().add(new Label(s));
      leftInfo.getChildren().add(h);
      h.setSpacing(10);
      h.setAlignment(Pos.CENTER);
      h.setPrefHeight(80);
    }
    holePar = new HBox();
    holePar.setPrefHeight(80);
    leftInfo.getChildren().add(holePar);
  }

  private void HBoxSetUp() {
    int i = 1;
    holeLength.getChildren().add(new Label("Lengde:"));
    holeIndex.getChildren().add(new Label("Hull:"));
    playerSetup();
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
    holeIndex.setAlignment(Pos.CENTER);
    holeLength.setAlignment(Pos.CENTER);
    holePar.setAlignment(Pos.CENTER);
  }


  private void leftInfoSet() {
    //leftInfo.getChildren().add(holeIndex);
    //leftInfo.getChildren().addAll(holeIndex, holeLength);
   // leftInfo.getChildren().addAll(holePar);



  }
}