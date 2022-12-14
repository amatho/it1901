package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import golfapp.data.CustomObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  void json_serializeAndDeserialize() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");
    var scorecard = new Scorecard(new Course("Test Course", List.of(new Hole(42, 3, 42))),
        List.of(user));
    user.addScorecard(scorecard);

    var json = CustomObjectMapper.SINGLETON.writeValueAsString(user);
    var deserializedUser = CustomObjectMapper.SINGLETON.readValue(json, User.class);

    assertTrue(user.deepEquals(deserializedUser));
  }

  @Test
  void constructor_makesUniqueUser() throws JsonProcessingException {
    var user1 = new User("foo@test.com", "Foo Bar");
    var user2 = new User("foo@test.com", "Foo Bar");

    assertNotEquals(user1, user2);
  }

  @Test
  void setEmail_validEmail() {
    User validUser = new User("foo@bar.baz", "foo bar");
    assertThrows(IllegalArgumentException.class, () -> new User("foobar.baz", "Foo Bar"));
    assertThrows(IllegalArgumentException.class, () -> new User("foo@bar", "Foo Bar"));
    assertThrows(IllegalArgumentException.class, () -> new User("foo@bar.b", "Foo Bar"));

  }
}
