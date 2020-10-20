package golfapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileGolfAppModelDao implements GolfAppModelDao {

  private static final Path dataFolder = getLocalUserDataFolder();
  private static final Path dataPath = dataFolder.resolve("golf-app-model.json");
  private final FilesWrapper files;

  public FileGolfAppModelDao() {
    this(new FilesWrapper());
  }

  /**
   * Uses the given {@link FilesWrapper} instead the default one.
   *
   * @param files the files wrapper
   */
  public FileGolfAppModelDao(FilesWrapper files) {
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

  private GolfAppModel createDefaultModel() {
    var reader = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream("default-courses.json"),
            StandardCharsets.UTF_8));
    var json = reader.lines().collect(Collectors.joining("\n"));
    try {
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Set<Course> courses;
    try {
      courses = new HashSet<>(
          MapperInstance.getInstance().readerFor(Course.class).<Course>readValues(json).readAll());
    } catch (Exception e) {
      throw new IllegalStateException("Could not read default courses", e);
    }

    var bookingSystems = new HashMap<Course, BookingSystem>(courses.size());
    courses.forEach(c -> bookingSystems.put(c, new BookingSystem()));

    return new GolfAppModel(new HashSet<>(), courses, bookingSystems);
  }

  private GolfAppModel readModel() {
    String json;
    try {
      json = files.readString(dataPath);
    } catch (IOException e) {
      // Assume that nothing has been saved before and return a new model
      return createDefaultModel();
    }

    try {
      return MapperInstance.getInstance().readValue(json, GolfAppModel.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not parse JSON correctly", e);
    }
  }

  private void writeModel(GolfAppModel model) {
    String json;
    try {
      json = MapperInstance.getInstance().writeValueAsString(model);
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
  public void addUser(User u) {
    var model = readModel();
    model.addUser(u);
    writeModel(model);
  }

  @Override
  public void updateUser(User u) {
    var model = readModel();
    model.updateUser(u);
    writeModel(model);
  }

  @Override
  public void deleteUser(User u) {
    var model = readModel();
    model.deleteUser(u);
    writeModel(model);
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
  public void updateBookingSystem(Course c, BookingSystem b) {
    var model = readModel();
    model.updateBookingSystem(c, b);
    writeModel(model);
  }
}
