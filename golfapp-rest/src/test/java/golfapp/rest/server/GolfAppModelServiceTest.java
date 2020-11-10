package golfapp.rest.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import golfapp.core.Booking;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import golfapp.data.BookingSystemsMapConverter;
import golfapp.data.CustomObjectMapper;
import golfapp.data.GolfAppModelDao;
import golfapp.data.MapEntry;
import golfapp.rest.api.GolfAppModelService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GolfAppModelServiceTest extends JerseyTest {

  @Override
  protected Application configure() {
    var modelDaoMock = mock(GolfAppModelDao.class);
    when(modelDaoMock.getModel()).thenReturn(GolfAppModel.createDefaultModel());
    return new GolfAppConfig(modelDaoMock);
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  private ObjectMapper mapper;

  @Override
  @BeforeEach
  public void setUp() throws Exception {
    super.setUp();
    mapper = new CustomObjectMapper();
  }

  @Override
  @AfterEach
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  void getGolfAppModel() throws JsonProcessingException {
    var response = target(GolfAppModelService.PATH)
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, response.getStatus());

    var golfAppModel = mapper
        .readValue(response.readEntity(String.class), GolfAppModel.class);

    assertEquals(GolfAppModel.createDefaultModel(), golfAppModel);
  }

  @Test
  void userResource_getUsers() throws JsonProcessingException {
    var response = target(GolfAppModelService.PATH + "/users")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, response.getStatus());

    var users = mapper.readValue(response.readEntity(String.class),
        new TypeReference<Set<User>>() {
        });

    assertNotNull(users);
  }

  @Test
  void userResource_addUser() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");

    var addResponse = target(GolfAppModelService.PATH + "/users")
        .request()
        .put(Entity.entity(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

    assertEquals(200, addResponse.getStatus());

    var getResponse = target(GolfAppModelService.PATH + "/users")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    var users = mapper.readValue(getResponse.readEntity(String.class),
        new TypeReference<Set<User>>() {
        });
    assertTrue(users.stream().anyMatch(u -> u.equals(user)));
  }

  @Test
  void userResource_updateUser() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");
    target(GolfAppModelService.PATH + "/users")
        .request()
        .put(Entity.entity(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

    user.setEmail("bar@example.com");
    user.setDisplayName("Foo Example");
    var postResponse = target(GolfAppModelService.PATH + "/users/" + user.getId())
        .request()
        .post(Entity.entity(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

    assertEquals(200, postResponse.getStatus());

    var getResponse = target(GolfAppModelService.PATH + "/users")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    var actualUser = mapper.readValue(getResponse.readEntity(String.class),
        new TypeReference<Set<User>>() {
        }).stream().filter(u -> u.equals(user)).findAny().orElseThrow();
    assertEquals(user.getEmail(), actualUser.getEmail());
    assertEquals(user.getDisplayName(), actualUser.getDisplayName());
  }

  @Test
  void userResource_deleteUser() throws JsonProcessingException {
    var user = new User("foo@foobar.com", "Foo Bar");
    target(GolfAppModelService.PATH + "/users")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .put(Entity.entity(mapper.writeValueAsString(user), MediaType.APPLICATION_JSON));

    var deleteResponse = target(GolfAppModelService.PATH + "/users/" + user.getId())
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .delete();

    assertEquals(200, deleteResponse.getStatus());

    var getResponse = target(GolfAppModelService.PATH + "/users")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    var users = mapper.readValue(getResponse.readEntity(String.class),
        new TypeReference<Set<User>>() {
        });
    assertFalse(users.stream().anyMatch(u -> u.equals(user)));
  }

  @Test
  void courseResource_getCourses() throws JsonProcessingException {
    var response = target(GolfAppModelService.PATH + "/courses")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, response.getStatus());

    var courses = mapper.readValue(response.readEntity(String.class),
        new TypeReference<Set<Course>>() {
        });

    assertTrue(courses.size() > 0);
  }

  @Test
  void bookingSystemResource_getBookingSystems() throws JsonProcessingException {
    var response = target(GolfAppModelService.PATH + "/bookingsystems")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, response.getStatus());

    var bookingSystemsList = mapper.readValue(response.readEntity(String.class),
        new TypeReference<List<MapEntry<Course, BookingSystem>>>() {
        });
    var bookingSystems = new BookingSystemsMapConverter().convert(bookingSystemsList);

    assertTrue(bookingSystems.size() > 0);
  }

  @Test
  void bookingSystemResource_updateBookingSystem() throws JsonProcessingException {
    var response = target(GolfAppModelService.PATH + "/bookingsystems")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    var bookingSystemsList = mapper.readValue(response.readEntity(String.class),
        new TypeReference<List<MapEntry<Course, BookingSystem>>>() {
        });
    var converter = new BookingSystemsMapConverter();
    var bookingSystems = converter.convert(bookingSystemsList);
    var entry = bookingSystems.entrySet().stream().findAny().orElseThrow();
    var course = entry.getKey();
    var bookingSystem = entry.getValue();

    var booking = new Booking(new User("foo@example.com", "Foo"),
        LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(11, 0)));
    bookingSystem.addBooking(booking);

    var postResponse = target(GolfAppModelService.PATH + "/bookingsystems/" + course.getId())
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.entity(mapper.writeValueAsString(bookingSystem), MediaType.APPLICATION_JSON));

    assertEquals(200, postResponse.getStatus());

    var getResponse = target(GolfAppModelService.PATH + "/bookingsystems")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get();

    var newBookingSystemList = mapper.readValue(getResponse.readEntity(String.class),
        new TypeReference<List<MapEntry<Course, BookingSystem>>>() {
        });
    var newBookingSystems = converter.convert(newBookingSystemList);

    assertEquals(newBookingSystems.get(course), bookingSystem);
  }
}
