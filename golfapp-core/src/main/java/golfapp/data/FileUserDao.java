package golfapp.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import golfapp.core.User;
import java.io.IOException;
import java.nio.file.Path;

public class FileUserDao implements UserDao {

  private static final Path userDataPath = getUserDataFolder().resolve("GolfApp/userdata.json");
  private final FilesWrapper files;

  public FileUserDao() {
    files = new FilesWrapper();
  }

  FileUserDao(FilesWrapper files) {
    this.files = files;
  }

  private static Path getUserDataFolder() {
    var osName = System.getProperty("os.name").toLowerCase();
    Path path;
    if (osName.startsWith("win")) {
      path = Path.of(System.getenv("LOCALAPPDATA"));
    } else if (osName.startsWith("mac")) {
      path = Path.of(System.getProperty("user.home"), "Library", "Application Support");
    } else {
      path = Path.of(System.getProperty("user.home"), ".local", "share");
    }

    return path;
  }

  // Create the user data folder if it does not exist
  private void createUserDataDir() {
    try {
      var userDataFolder = userDataPath.getParent();

      if (userDataFolder == null) {
        throw new IllegalStateException("User data path was not set to a valid path");
      }

      files.createDirectories(userDataFolder);
    } catch (IOException e) {
      throw new IllegalStateException("Could create directory in user data", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(User user) {
    try {
      var mapper = new ObjectMapper();
      var json = mapper.writeValueAsString(user);
      createUserDataDir();
      files.writeString(userDataPath, json);
    } catch (IOException e) {
      throw new IllegalStateException("Could not save user to file", e);
    }
  }

  /**
   * {@inheritDoc}
   *
   * @throws IllegalStateException if the user could not be loaded from file
   */
  @Override
  public User load() {
    try {
      var mapper = new ObjectMapper();
      var json = files.readString(userDataPath);
      return mapper.readValue(json, User.class);
    } catch (IOException e) {
      throw new IllegalStateException("Could not load user from file", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists() {
    return files.exists(userDataPath);
  }
}
