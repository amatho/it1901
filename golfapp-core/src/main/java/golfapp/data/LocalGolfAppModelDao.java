package golfapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class LocalGolfAppModelDao implements GolfAppModelDao {

  private static final Path dataFolder = getLocalUserDataFolder();
  private static final Path dataPath = dataFolder.resolve("golf-app-model.json");
  private final FilesWrapper files;

  public LocalGolfAppModelDao() {
    this(new FilesWrapper());
  }

  /**
   * Uses the given {@link FilesWrapper} instead the default one.
   *
   * @param files the files wrapper
   */
  public LocalGolfAppModelDao(FilesWrapper files) {
    this.files = files;

    createDataDir();
  }

  private static Path getLocalUserDataFolder() {
    var osName = System.getProperty("os.name").toLowerCase();

    Path path;
    if (osName.startsWith("win")) {
      path = Path.of(System.getenv("LOCALAPPDATA"));
    } else if (osName.startsWith("mac")) {
      path = Path.of(System.getProperty("user.home"), "Library", "Application Support");
    } else {
      path = Path.of(System.getProperty("user.home"), ".local", "share");
    }

    return path.resolve("GolfApp");
  }

  // Create the data folder if it does not exist
  private void createDataDir() {
    try {
      files.createDirectories(dataFolder);
    } catch (IOException e) {
      throw new IllegalStateException("Could create directory in data path", e);
    }
  }

  private GolfAppModel readModel() {
    String json;
    try {
      json = files.readString(dataPath);
    } catch (IOException e) {
      // Assume that nothing has been saved before and return a new model
      return GolfAppModel.createDefaultModel();
    }

    try {
      return CustomObjectMapper.SINGLETON.readValue(json, GolfAppModel.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not parse JSON correctly", e);
    }
  }

  private void writeModel(GolfAppModel model) {
    String json;
    try {
      json = CustomObjectMapper.SINGLETON.writeValueAsString(model);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not parse JSON correctly", e);
    }

    try {
      files.writeString(dataPath, json);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write JSON to file", e);
    }
  }

  @Override
  public GolfAppModel getModel() {
    return readModel();
  }

  @Override
  public Set<User> getUsers() {
    var model = readModel();
    return model.getUsers();
  }

  @Override
  public boolean addUser(User u) {
    var model = readModel();
    var wasAdded = model.addUser(u);
    writeModel(model);

    return wasAdded;
  }

  @Override
  public boolean updateUser(User u) {
    var model = readModel();
    var wasUpdated = model.updateUser(u);
    writeModel(model);

    return wasUpdated;
  }

  @Override
  public boolean deleteUser(User u) {
    var model = readModel();
    var wasDeleted = model.deleteUser(u);
    writeModel(model);

    return wasDeleted;
  }

  @Override
  public Set<Course> getCourses() {
    var model = readModel();
    return model.getCourses();
  }

  @Override
  public Map<Course, BookingSystem> getBookingSystems() {
    var model = readModel();
    return model.getBookingSystems();
  }

  @Override
  public boolean updateBookingSystem(Course c, BookingSystem b) {
    var model = readModel();
    var wasUpdated = model.updateBookingSystem(c, b);
    writeModel(model);

    return wasUpdated;
  }
}
