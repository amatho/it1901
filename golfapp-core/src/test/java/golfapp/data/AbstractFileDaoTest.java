package golfapp.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AbstractFileDaoTest {

  private static class FileStringDao extends AbstractFileDao<String> {

    private FileStringDao(FilesWrapper files) {
      super(files);
    }

    @Override
    String getFilename() {
      return "test.json";
    }

    @Override
    Class<?> targetClass() {
      return String.class;
    }
  }

  private static final String testJson = """
      [
        "foo",
        "bar",
        "baz",
        "foobar",
        "foobar baz"
      ]
      """;

  @Test
  void get_indexesIntoListFromJson() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(testJson);
    var expected = "bar";
    var fileDao = new FileStringDao(files);

    var actual = fileDao.get(1).orElseThrow();
    var notFound = fileDao.get(5);

    assertEquals(expected, actual);
    assertTrue(notFound.isEmpty());
  }

  @Test
  void getAll_returnsListOfAll() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(testJson);
    List<ObjectContainer<String>> expected = List.of(
        new ObjectContainer<>(0, "foo"),
        new ObjectContainer<>(1, "bar"),
        new ObjectContainer<>(2, "baz"),
        new ObjectContainer<>(3, "foobar"),
        new ObjectContainer<>(4, "foobar baz"));
    var fileDao = new FileStringDao(files);

    var strings = fileDao.getAll();

    assertIterableEquals(expected, strings);
  }

  @Test
  void getAll_returnsEmptyListIfNoneSaved() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenThrow(new IOException("File not found"));
    var fileDao = new FileStringDao(files);

    var strings = fileDao.getAll();

    assertTrue(strings.isEmpty());
  }

  @Test
  void save_updatesListAndSavesToFile() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(testJson);
    var expected = """
        ["foo","bar","baz","foobar","foobar baz","new string"]""";
    var fileDao = new FileStringDao(files);

    fileDao.save("new string");

    verify(files).writeString(any(Path.class), eq(expected));
  }

  @Test
  void update_setsArrayAndSavesToFile() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(testJson);
    var expected = """
        ["foo","bar","baz 42","foobar","foobar baz"]""";
    var fileDao = new FileStringDao(files);

    fileDao.update(2, "baz 42");

    verify(files).writeString(any(Path.class), eq(expected));
  }

  @Test
  void delete_removesFromListAndSavesToFile() throws IOException {
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(testJson);
    var expected = """
        ["foo","bar","baz","foobar baz"]""";
    var fileDao = new FileStringDao(files);

    fileDao.delete(3);

    verify(files).writeString(any(Path.class), eq(expected));
  }
}
