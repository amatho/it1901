package core;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveHandler {

  // TODO: Change to something more meaningful or a different path
  private static final Path filename = Path.of("userdata.json");

  /**
   * Saves a user to file.
   *
   * @param user the user to save
   */
  public void save(User user) {
    try {
      var gson = new Gson();
      var json = gson.toJson(user);
      var file = Files.createFile(filename);
      Files.writeString(file, json);
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
      var json = Files.readString(filename);
      var user = gson.fromJson(json, User.class);
      return user;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }
}
