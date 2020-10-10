package golfapp.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public abstract class AbstractFileDao<T> implements Dao<T> {

  private static final Path localUserDataFolder = getLocalUserDataFolder();
  private final FilesWrapper files;

  public AbstractFileDao() {
    this(new FilesWrapper());
  }

  AbstractFileDao(FilesWrapper files) {
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

  abstract String getFilename();

  abstract Class<?> targetClass();

  private Path getDataPath() {
    return localUserDataFolder.resolve(getFilename());
  }

  // Create the data folder if it does not exist
  private void createDataDir() {
    try {
      files.createDirectories(localUserDataFolder);
    } catch (IOException e) {
      throw new IllegalStateException("Could create directory in data path", e);
    }
  }

  void writeTs(List<T> ts) {
    String json;
    try {
      json = MapperInstance.getInstance().writeValueAsString(ts);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Could not write objects as JSON", e);
    }

    try {
      files.writeString(getDataPath(), json);
    } catch (IOException e) {
      throw new IllegalStateException("Could not write JSON to file", e);
    }
  }

  List<T> readTs() {
    String json;
    try {
      json = files.readString(getDataPath());
    } catch (IOException e) {
      // Assume that nothing has been saved before
      return new ArrayList<>();
    }

    try {
      return MapperInstance.getInstance().readerFor(targetClass()).<T>readValues(json).readAll();
    } catch (IOException e) {
      throw new IllegalStateException("Could not parse JSON correctly", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<T> get(long id) {
    var ts = readTs();

    try {
      return Optional.of(ts.get((int) id));
    } catch (IndexOutOfBoundsException e) {
      return Optional.empty();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ObjectContainer<T>> getAll() {
    var ts = readTs();

    return LongStream.range(0, ts.size())
        .mapToObj(i -> new ObjectContainer<>(i, ts.get((int) i))).collect(Collectors.toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long save(T t) {
    var ts = readTs();
    final var id = ts.size();
    ts.add(t);

    writeTs(ts);

    return id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(long id, T t) {
    var ts = readTs();
    ts.set((int) id, t);
    writeTs(ts);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(long id) {
    var ts = readTs();
    ts.remove((int) id);
    writeTs(ts);
  }
}
