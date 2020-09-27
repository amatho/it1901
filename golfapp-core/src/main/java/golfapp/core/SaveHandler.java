package golfapp.core;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveHandler {

  // TODO: Change to something more meaningful or a different path
  private static final Path filename = Path.of("userdata.json");
  private final FilesWrapper files;

  public SaveHandler() {
    files = new FilesWrapper();
  }

  SaveHandler(FilesWrapper files) {
    this.files = files;
  }

  /**
   * Saves a user to file.
   *
   * @param user the user to save
   */
  public void save(User user) {
    try {
      var gson = new Gson();
      var json = gson.toJson(user);
      var file = files.createFile(filename);
      files.writeString(file, json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load a user from file.
   *
   * @return the loaded user. Returns null if the file was not found or the JSON is invalid
   */
  public User load() {
    try {
      var gson = new Gson();
      var json = files.readString(filename);
      return gson.fromJson(json, User.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}

class FilesWrapper {

  public Path createFile(Path path) throws IOException {
    return Files.createFile(path);
  }

  public void writeString(Path path, CharSequence csq) throws IOException {
    Files.writeString(path, csq);
  }

  public String readString(Path path) throws IOException {
    return Files.readString(path);
  }
}
