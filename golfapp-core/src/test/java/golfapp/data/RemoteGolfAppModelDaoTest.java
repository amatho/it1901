package golfapp.data;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.net.URI;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RemoteGolfAppModelDaoTest {

  private WireMockServer wireMockServer;
  private RemoteGolfAppModelDao remoteDao;
  private static GolfAppModel golfAppModel;
  private static CustomObjectMapper mapper;

  @BeforeAll
  static void beforeAll() {
    golfAppModel = GolfAppModel.createDefaultModel();
    mapper = new CustomObjectMapper();
  }

  @BeforeEach
  void setup() {
    var config = WireMockConfiguration.wireMockConfig().port(8089);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    remoteDao = new RemoteGolfAppModelDao(
        URI.create("http://localhost:" + wireMockServer.port() + "/golfapp/"));
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @Test
  void getModel() throws JsonProcessingException {
    stubGet("/golfapp/", golfAppModel);

    var actual = remoteDao.getModel();
    assertEquals(golfAppModel, actual);
  }

  @Test
  void getUsers() throws JsonProcessingException {
    stubGet("/golfapp/users", golfAppModel.getUsers());

    var actual = remoteDao.getUsers();
    assertEquals(golfAppModel.getUsers(), actual);
  }

  @Test
  void addUser() throws JsonProcessingException {
    var user = new User("foo@example.com", "Foo");
    var userJson = mapper.writeValueAsBytes(user);

    wireMockServer.stubFor(put("/golfapp/users")
        .andMatching(r -> MatchResult.of(Arrays.equals(r.getBody(), userJson)))
        .willReturn(okJson("true")));

    remoteDao.addUser(user);
  }

  @Test
  void updateUser() throws JsonProcessingException {
    var user = new User("bar@example.com", "Bar");
    var userJson = mapper.writeValueAsBytes(user);

    wireMockServer.stubFor(post("/golfapp/users/" + user.getId())
        .andMatching(r -> MatchResult.of(Arrays.equals(r.getBody(), userJson)))
        .willReturn(okJson("true")));

    assertTrue(remoteDao.updateUser(user));
  }

  @Test
  void deleteUser() {
    var user = new User("bar@example.com", "Bar");

    wireMockServer.stubFor(delete("/golfapp/users/" + user.getId())
        .willReturn(okJson("true")));

    remoteDao.deleteUser(user);
  }

  @Test
  void getCourses() throws JsonProcessingException {
    stubGet("/golfapp/courses", golfAppModel.getCourses());
  }

  @Test
  void getBookingSystems() throws JsonProcessingException {
    var converter = new BookingSystemsListConverter();
    var bookingSystemsList = converter.convert(golfAppModel.getBookingSystems());
    stubGet("/golfapp/bookingsystems", bookingSystemsList);

    var actual = remoteDao.getBookingSystems();
    assertEquals(golfAppModel.getBookingSystems(), actual);
  }

  @Test
  void updateBookingSystem() throws JsonProcessingException {
    var entry = golfAppModel.getBookingSystems().entrySet().stream().findAny().orElseThrow();
    var course = entry.getKey();
    var bookingSystem = entry.getValue();
    var bookingSystemJson = mapper.writeValueAsBytes(bookingSystem);

    wireMockServer.stubFor(post("/golfapp/bookingsystems/" + course.getId())
        .andMatching(r -> MatchResult.of(Arrays.equals(r.getBody(), bookingSystemJson)))
        .willReturn(okJson("true")));

    remoteDao.updateBookingSystem(course, bookingSystem);
  }

  private void stubGet(String path, Object bodyValue) throws JsonProcessingException {
    wireMockServer.stubFor(get(path)
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-type", "application/json")
            .withBody(mapper.writeValueAsBytes(bodyValue))));
  }
}
