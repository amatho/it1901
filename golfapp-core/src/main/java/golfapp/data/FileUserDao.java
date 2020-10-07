package golfapp.data;

import golfapp.core.User;
import java.io.IOException;
import java.nio.file.Path;

public class FileUserDao implements UserDao {

  // TODO: Change to something more meaningful or a different path
  private static final Path filename = Path.of("userdata.json");
  private final FilesWrapper files;

  public FileUserDao() {
    files = new FilesWrapper();
  }

  FileUserDao(FilesWrapper files) {
    this.files = files;
  }

  /**
   * Saves a user to file.
   *
   * @param user the user to save
   */
  @Override
  public void save(User user) {
    try {
      var mapper = MapperInstance.getInstance();
      var json = mapper.writeValueAsString(user);
      var file = files.createFile(filename);
      files.writeString(file, json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load a user from file.
   *
   * @return the loaded user. Returns null if the file was not found
   */
  @Override
  public User load() {
    try {
      var mapper = MapperInstance.getInstance();
      var json = files.readString(filename);
      return mapper.readValue(json, User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
