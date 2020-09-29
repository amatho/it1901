package golfapp.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesWrapper {

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
