package golfapp.gui;

import golfapp.core.BookingSystem;
import golfapp.core.Course;
import golfapp.core.GolfAppModel;
import golfapp.core.User;
import golfapp.data.FilesWrapper;
import golfapp.data.GolfAppModelDao;
import golfapp.data.LocalGolfAppModelDao;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

class StringGolfAppModelDao implements GolfAppModelDao {

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

  private final LocalGolfAppModelDao fileModelDao;

  StringGolfAppModelDao() {
    this(true);
  }

  StringGolfAppModelDao(boolean throwIfFileContentsEmpty) {
    var stringFile = new StringFile();
    stringFile.throwIfFileContentsEmpty = throwIfFileContentsEmpty;
    fileModelDao = new LocalGolfAppModelDao(stringFile);
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
  public boolean addUser(User u) {
    return fileModelDao.addUser(u);
  }

  @Override
  public boolean updateUser(User u) {
    return fileModelDao.updateUser(u);
  }

  @Override
  public boolean deleteUser(User u) {
    return fileModelDao.deleteUser(u);
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
  public boolean updateBookingSystem(Course c, BookingSystem b) {
    return fileModelDao.updateBookingSystem(c, b);
  }
}
