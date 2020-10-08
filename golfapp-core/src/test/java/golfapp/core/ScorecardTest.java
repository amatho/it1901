package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ScorecardTest {

  @Test
  public void constructor_checksPlayerCount() {
    var hole = new Hole(42, 3, 42);
    var course = new Course("course", List.of(hole));
    var users = new ArrayList<User>();
    for (var i = 0; i < 5; i++) {
      users.add(new User(i + "@test", "User " + i));
    }

    assertThrows(IllegalArgumentException.class, () -> new Scorecard(course, users));
    assertDoesNotThrow(() -> new Scorecard(course, users.subList(0, 4)));
  }

  @Test
  public void getScore_getsScoreForUserAndHole() {
    var hole1 = new Hole(195, 3, 23);
    var hole2 = new Hole(455, 5, 10);
    var course = new Course("course", List.of(hole1));
    var user1 = new User("ola@test.no", "Ola Nordmann");
    var user2 = new User("kari@test.no", "Kari Nordmann");
    var scorecard = new Scorecard(course, List.of(user1, user2));

    scorecard.setScore(user1, hole1, 5);

    assertEquals(5, scorecard.getScore(user1, hole1));
    assertThrows(IllegalArgumentException.class, () -> scorecard.getScore(user1, hole2));
  }

  @Test
  public void setScore_checksHoleAndScore() {
    var hole1 = new Hole(195, 3, 23);
    final var hole2 = new Hole(455, 5, 10);
    var course = new Course("course", List.of(hole1));
    var user1 = new User("ola@test.no", "Ola Nordmann");
    var user2 = new User("kari@test.no", "Kari Nordmann");
    var scorecard = new Scorecard(course, List.of(user1, user2));

    scorecard.setScore(user1, hole1, 4);

    assertEquals(4, scorecard.getScore(user1, hole1));
    assertThrows(IllegalArgumentException.class, () -> scorecard.setScore(user2, hole1, 0));
    assertThrows(IllegalArgumentException.class, () -> scorecard.setScore(user2, hole2, 4));
  }

  @Test
  public void getTotalScore_returnsCorrectSum() {
    var hole1 = new Hole(195, 3, 23);
    var hole2 = new Hole(455, 5, 10);
    var course = new Course("course", List.of(hole1, hole2));
    var user1 = new User("ola@test.no", "Ola Nordmann");
    var user2 = new User("kari@test.no", "Kari Nordmann");
    var scorecard = new Scorecard(course, List.of(user1, user2));

    scorecard.setScore(user1, hole1, 2);
    scorecard.setScore(user1, hole2, 6);
    scorecard.setScore(user2, hole1, 7);
    scorecard.setScore(user2, hole2, 3);

    assertEquals(8, scorecard.getTotalScore(user1));
    assertEquals(10, scorecard.getTotalScore(user2));
  }
}
