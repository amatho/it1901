package core;

import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScorecardTest {

  @Test
  public void testConstructor() {
    var course = new Course(new Hole(42, 3, 42));
    var users = new ArrayList<User>();
    for (var i = 0; i < 5; i++) {
      users.add(new User("User " + i));
    }

    Assertions.assertThrows(IllegalArgumentException.class, () -> new Scorecard(course, users));
    var scorecard = Assertions.assertDoesNotThrow(() -> new Scorecard(course, users.subList(0, 4)));
    Assertions.assertEquals(course, scorecard.getCourse());
  }
}
