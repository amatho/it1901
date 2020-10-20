package golfapp.gui;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import golfapp.data.FileGolfAppModelDao;
import golfapp.data.FilesWrapper;
import golfapp.data.GolfAppModelDao;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

class InMemoryGolfAppModelDao implements GolfAppModelDao {

  private static class StringFile extends FilesWrapper {

    private String fileContents = "";
    private boolean throwIfFileContentsEmpty = true;

    @Override
    public String readString(Path path) throws IOException {
      if (throwIfFileContentsEmpty && fileContents.isBlank()) {
        throw new IOException("Could not read file");
      }

      return fileContents;
    }

    @Override
    public void writeString(Path path, CharSequence csq) {
      fileContents = csq.toString();
    }
  }

  private final FileGolfAppModelDao fileModelDao;

  InMemoryGolfAppModelDao() {
    this(true);
  }

  InMemoryGolfAppModelDao(boolean throwIfFileContentsEmpty) {
    var stringFile = new StringFile();
    stringFile.throwIfFileContentsEmpty = throwIfFileContentsEmpty;
    fileModelDao = new FileGolfAppModelDao(stringFile);
  }

  @Override
  public GolfAppModel getModel() {
    return fileModelDao.getModel();
  }

  @Override
  public Set<User> getUsers() {
    return fileModelDao.getUsers();
  }

  @Override
  public void addUser(User u) {
    fileModelDao.addUser(u);
  }

  @Override
  public void updateUser(User u) {
    fileModelDao.updateUser(u);
  }

  @Override
  public void deleteUser(User u) {
    fileModelDao.deleteUser(u);
  }

  @Override
  public Set<Course> getCourses() {
    return fileModelDao.getCourses();
  }

  @Override
  public Map<Course, BookingSystem> getBookingSystems() {
    return fileModelDao.getBookingSystems();
  }

  @Override
  public void updateBookingSystem(Course c, BookingSystem b) {
    fileModelDao.updateBookingSystem(c, b);
  }
}
