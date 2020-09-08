package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class SaveHandlerTest {

  @Test
  public void testSave() throws IOException {
    var files = mock(FilesWrapper.class);
    var saveHandler = new SaveHandler(files);
    var user = new User("foobar");

    saveHandler.save(user);

    verify(files).createFile(any());
    verify(files).writeString(any(), any());
  }

  @Test
  public void testLoad() throws IOException {
    var json = "{\"username\":\"foobar\",\"userID\":\"cd7c149e-74d6-451a-a811-097a9e2b491f\"}";
    var files = mock(FilesWrapper.class);
    when(files.readString(any())).thenReturn(json);
    var saveHandler = new SaveHandler(files);

    var user = saveHandler.load();

    assertEquals("foobar", user.getUsername());
    assertEquals(UUID.fromString("cd7c149e-74d6-451a-a811-097a9e2b491f"), user.getUserID());
  }
}
