package golfapp.core;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScorecardTest {

  @Test
  public void testConstructor() {
    List<Hole> holes = new ArrayList<>();
    Hole hole = new Hole(42, 3, 42);
    holes.add(hole);
    var course = new Course(holes);
    var users = new ArrayList<User>();
    for (var i = 0; i < 5; i++) {
      users.add(new User("User " + i));
    }

    Assertions.assertThrows(IllegalArgumentException.class, () -> new Scorecard(course, users));
    var scorecard = Assertions.assertDoesNotThrow(() -> new Scorecard(course, users.subList(0, 4)));
    Assertions.assertEquals(course, scorecard.getCourse());
  }


  @Test
  public void testGetScore() {
    Hole hole1 = new Hole(195, 3, 23);
    Hole hole2 = new Hole(455, 5, 10);
    List<Hole> holes = List.of(hole1);
    Course course1 = new Course(holes);
    User user1 = new User("Ola Nordmann");
    User user2 = new User("Kari Nordmann");
    List<User> users = List.of(user1, user2);
    Scorecard scorecard1 = new Scorecard(course1, users);
    scorecard1.setScore(user1, hole2, 5);
    Assertions
        .assertThrows(IllegalArgumentException.class, () -> scorecard1.getScore(user1, hole2));
  }

  @Test
  public void testSetScore() {
    Hole hole1 = new Hole(195, 3, 23);
    Hole hole2 = new Hole(455, 5, 10);
    List<Hole> holes = List.of(hole1);
    Course course1 = new Course(holes);
    User user1 = new User("Ola Nordmann");
    User user2 = new User("Kari Nordmann");
    List<User> users = List.of(user1, user2);
    Scorecard scorecard1 = new Scorecard(course1, users);
    scorecard1.setScore(user1, hole1, 4);

    Assertions.assertEquals(4, scorecard1.getScore(user1, hole1));
    Assertions
        .assertThrows(IllegalArgumentException.class, () -> scorecard1.setScore(user2, hole1, 0));
    Assertions
        .assertThrows(IllegalArgumentException.class, () -> scorecard1.setScore(user2, hole2, 4));
  }

  @Test
  public void testGetTotalScore() {
    Hole hole1 = new Hole(195, 3, 23);
    Hole hole2 = new Hole(455, 5, 10);
    List<Hole> holes = List.of(hole1, hole2);
    Course course1 = new Course(holes);
    User user1 = new User("Ola Nordmann");
    User user2 = new User("Kari Nordmann");
    List<User> users = List.of(user1, user2);
    Scorecard scorecard1 = new Scorecard(course1, users);
    scorecard1.setScore(user1, hole1, 4);
    scorecard1.setScore(user1, hole2, 4);
    scorecard1.setScore(user2, hole1, 5);
    scorecard1.setScore(user2, hole2, 5);

    Assertions.assertEquals(8, scorecard1.getTotalScore(user1));
    Assertions.assertEquals(10, scorecard1.getTotalScore(user2));

  }
}
