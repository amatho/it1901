package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import golfapp.data.MapperInstance;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class UserTest {
  
  @Disabled
  @Test
  void json_serializeAndDeserialize() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");
    var scorecard = new Scorecard(new Course("Test Course", List.of(new Hole(42, 3, 42))),
        List.of(user));
    user.addScorecard(scorecard);

    var json = MapperInstance.getInstance().writeValueAsString(user);
    var deserializedUser = MapperInstance.getInstance().readValue(json, User.class);

    assertTrue(user.deepEquals(deserializedUser));
  }

  @Test
  void constructor_makesUniqueUser() throws JsonProcessingException {
    var user1 = new User("foo@test.com", "Foo Bar");
    var user2 = new User("foo@test.com", "Foo Bar");

    assertNotEquals(user1, user2);
  }
}
