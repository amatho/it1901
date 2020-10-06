package golfapp.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import golfapp.core.User;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class FileUserDaoTest {

  @Test
  public void save_callsFilesWrapperMethods() throws IOException {
    var files = mock(FilesWrapper.class);
    var saveHandler = new FileUserDao(files);
    var user = new User("foobar");

    saveHandler.save(user);

    verify(files).writeString(any(Path.class), anyString());
  }

  @Test
  public void load_parsesJsonCorrectly() throws IOException {
    var json = """
        {
          "username": "foobar",
          "userId": "cd7c149e-74d6-451a-a811-097a9e2b491f"
        }
        """;
    var files = mock(FilesWrapper.class);
    when(files.readString(any(Path.class))).thenReturn(json);
    var saveHandler = new FileUserDao(files);

    var user = saveHandler.load();

    assertEquals("foobar", user.getUsername());
    assertEquals(UUID.fromString("cd7c149e-74d6-451a-a811-097a9e2b491f"), user.getUserId());
  }
}
