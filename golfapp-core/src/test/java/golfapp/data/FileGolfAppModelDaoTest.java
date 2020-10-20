package golfapp.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import golfapp.core.Booking;
import golfapp.core.User;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class FileGolfAppModelDaoTest {

  private static class StringFile extends FilesWrapper {

    private String fileContents = "";
    private boolean hasCreatedDirectory = false;
    private boolean shouldThrowWhenReadString = false;
    private int readCount = 0;
    private int writeCount = 0;

    @Override
    public String readString(Path path) throws IOException {
      if (shouldThrowWhenReadString) {
        throw new IOException("Could not read file");
      }

      readCount++;
      return fileContents;
    }

    @Override
    public void writeString(Path path, CharSequence csq) {
      fileContents = csq.toString();
      writeCount++;
    }

    @Override
    public void createDirectories(Path dir) {
      hasCreatedDirectory = true;
    }
  }

  private static final String testJson = """
      {
          "users": [
            {
              "id": "c5e3db21-ee0d-456e-97c0-ac136b76eafd",
              "email": "foo@test.com",
              "displayName": "Foo Bar"
            }
          ],
          "courses": [
            {
              "id": "31adc2be-b6a9-4a1c-8dc6-20dc5a3dc5c0",
              "name": "Test golf club",
              "holes": [
                {
                  "length": "42",
                  "par": "3",
                  "height": "4242"
                }
              ]
            }
          ],
          "bookingSystems": [
            {
              "course": "31adc2be-b6a9-4a1c-8dc6-20dc5a3dc5c0",
              "bookingSystem": {}
            }
          ]
      }
      """;

  @Test
  void constructor_createsDirectories() {
    var stringFile = new StringFile();

    new FileGolfAppModelDao(stringFile);

    assertTrue(stringFile.hasCreatedDirectory);
  }

  @Test
  void getModel_createsDefaultModelIfNothingWrittenBefore() {
    var stringFile = new StringFile();
    stringFile.shouldThrowWhenReadString = true;
    var modelDao = new FileGolfAppModelDao(stringFile);

    var model = modelDao.getModel();

    assertNotNull(model);
    assertTrue(model.getCourses().size() >= 1);
    assertEquals(model.getCourses().size(), model.getBookingSystems().entrySet().size());
    assertNotNull(model.getUsers());
  }

  @Test
  void getModel_readsFromFile() {
    var stringFile = new StringFile();
    stringFile.fileContents = testJson;
    var modelDao = new FileGolfAppModelDao(stringFile);

    var model = modelDao.getModel();

    var user = model.getUsers().stream().findFirst().orElseThrow();
    assertEquals("foo@test.com", user.getEmail());
    assertEquals("Foo Bar", user.getDisplayName());
    var course = model.getCourses().stream().findFirst().orElseThrow();
    assertEquals("Test golf club", course.getName());
    assertEquals(1, course.getHoles().size());
    assertEquals(course, model.getBookingSystems().keySet().stream().findFirst().orElseThrow());
  }

  @Test
  void getters_readsFromModel() {
    var stringFile = new StringFile();
    stringFile.fileContents = testJson;
    var modelDao = new FileGolfAppModelDao(stringFile);

    modelDao.getUsers();
    modelDao.getCourses();
    modelDao.getBookingSystems();

    assertEquals(3, stringFile.readCount);
    assertEquals(0, stringFile.writeCount);
  }

  @Test
  void userMethods_readsAndWritesModel() {
    var stringFile = new StringFile();
    stringFile.fileContents = testJson;
    var modelDao = new FileGolfAppModelDao(stringFile);
    var model = modelDao.getModel();
    var user = model.getUsers().stream().findFirst().orElseThrow();
    user.setDisplayName("Foo Bar Baz");
    var user2 = new User("test@example.com", "Foo To Delete");

    modelDao.addUser(new User("foo@example.com", "Foo Test"));
    modelDao.addUser(user2);
    modelDao.updateUser(user);
    modelDao.deleteUser(user2);

    assertEquals(5, stringFile.readCount);
    assertEquals(4, stringFile.writeCount);
    assertNotEquals(testJson, stringFile.fileContents);
  }

  @Test
  void bookingSystemsMethods_readsAndWritesModel() {
    var stringFile = new StringFile();
    stringFile.fileContents = testJson;
    var modelDao = new FileGolfAppModelDao(stringFile);

    var entry = modelDao.getBookingSystems().entrySet().stream().findFirst().orElseThrow();
    entry.getValue().addBooking(new Booking(new User("test@foo.com", "Test Foo"),
        LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14, 0))));
    modelDao.updateBookingSystem(entry.getKey(), entry.getValue());

    assertEquals(2, stringFile.readCount);
    assertEquals(1, stringFile.writeCount);
    assertNotEquals(testJson, stringFile.fileContents);
  }
}
