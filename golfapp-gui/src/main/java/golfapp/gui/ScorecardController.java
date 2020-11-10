package golfapp.gui;

import golfapp.core.Course;
import golfapp.core.GuestUser;
import golfapp.core.Hole;
import golfapp.core.Scorecard;
import golfapp.core.User;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.VBox;

public class ScorecardController {

  private final AppManager appManager;
  private final List<User> users;
  private final Scorecard scorecard;

  @FXML
  Label courseName;
  @FXML
  Label holeNr;
  @FXML
  Label holePar;
  @FXML
  Label holeHeight;
  @FXML
  Label holeLength;
  @FXML
  VBox playerInputs;
  @FXML
  Pagination holes;
  @FXML
  Button finishButton;

  /**
   * Create a new ScorecardController.
   *
   * @param appManager the app manager
   * @param users      the list of users
   * @param course     the course to implement in the scorecard attribute
   */
  public ScorecardController(AppManager appManager, ObservableList<User> users, Course course) {
    this.appManager = appManager;
    this.users = users;
    this.scorecard = new Scorecard(course, users);
  }

  @FXML
  void initialize() {
    courseName.setText(scorecard.getCourse().getName());
    holes.setPageCount(scorecard.getCourse().getCourseLength());
    holes.setCurrentPageIndex(0);
    holes.currentPageIndexProperty().addListener((observable, oldVal, newVal) -> {
      updateScores(oldVal.intValue());
      updatePlayerInputs();
      updateHoleInfo();
    });
    updatePlayerInputs();
    updateHoleInfo();
  }

  private void updatePlayerInputs() {
    playerInputs.getChildren().clear();
    Hole currentHole = scorecard.getCourse().getHole(holes.getCurrentPageIndex());
    for (var user : users) {
      var playerInput = new PlayerScoreInput(user);
      playerInput.setScore(scorecard.getScore(user, currentHole));
      playerInputs.getChildren().add(playerInput);

      boolean visible = holes.getCurrentPageIndex() == scorecard.getCourse().getCourseLength() - 1;
      finishButton.setVisible(visible);
    }
  }

  private void updateHoleInfo() {
    Hole hole = scorecard.getCourse().getHole(holes.getCurrentPageIndex());
    holeNr.setText("Hole number: " + (scorecard.getCourse().getHoleIndex(hole) + 1));
    holeHeight.setText("Height: " + hole.getHeight());
    holePar.setText("Par: " + hole.getPar());
    holeLength.setText("Length: " + hole.getLength());
  }

  private void updateScores(int holeIndex) {
    Hole hole = scorecard.getCourse().getHole(holeIndex);
    for (Node node : playerInputs.getChildren()) {
      PlayerScoreInput psi = (PlayerScoreInput) node;
      User user = psi.getUser();
      int score = psi.getScore();
      scorecard.setScore(user, hole, score);
    }
  }

  @FXML
  void handleFinishScorecard() {
    updateScores(holes.getCurrentPageIndex());
    for (User u : users) {
      if (u instanceof GuestUser) {
        continue;
      }

      u.addScorecard(scorecard);
      appManager.getModelDao().updateUser(u);
    }
    appManager.loadView("CreateScorecard.fxml", CreateScorecardController::new);
  }

  @FXML
  void handleGoBackButton() {
    appManager.loadView("CreateScorecard.fxml", CreateScorecardController::new);
  }
}
