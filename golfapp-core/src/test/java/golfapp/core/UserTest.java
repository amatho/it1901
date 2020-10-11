package golfapp.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import golfapp.data.MapperInstance;
import java.util.List;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  void json_serializing() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");
    var scorecard = new Scorecard(new Course("Test Course", List.of(new Hole(42, 3, 42))),
        List.of(user));
    user.addScorecard(scorecard);
    var localDateNow = MapperInstance.getInstance().writeValueAsString(scorecard.getDate());

    var json = MapperInstance.getInstance().writeValueAsString(user);

    var expected = """
        {"email":"foo@foobar.com","displayName":"Foo Bar","scorecardHistory":[{"course":{"name":\
        "Test Course","holes":[{"length":42.0,"par":3,"height":42.0}]},"scorecard":\
        {"foo@foobar.com":[0]},"date":""" + localDateNow + "}]}";
    assertEquals(expected, json);
  }

  @Test
  void json_deserializing() throws JsonProcessingException {
    var expected = new User("foo@foobar.com", "Foo Bar");
    var scorecard = new Scorecard(new Course("Test Course", List.of(new Hole(42, 3, 42))),
        List.of(expected));
    expected.addScorecard(scorecard);
    var localDateNow = MapperInstance.getInstance().writeValueAsString(scorecard.getDate());
    var json = """
        {"email":"foo@foobar.com","displayName":"Foo Bar","scorecardHistory":[{"course":{"name":\
        "Test Course","holes":[{"length":42.0,"par":3,"height":42.0}]},"scorecard":\
        {"foo@foobar.com":[0]},"date":""" + localDateNow + "}]}";

    var user = MapperInstance.getInstance().readValue(json, User.class);

    assertEquals(expected, user);
    assertEquals(expected.getDisplayName(), user.getDisplayName());
    assertIterableEquals(expected.getScorecardHistory(), user.getScorecardHistory());
  }
}
