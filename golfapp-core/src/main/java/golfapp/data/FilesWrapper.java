package golfapp.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public class FilesWrapper {

  /**
   * Delegates to {@link Files#createFile(Path, FileAttribute...)}.
   *
   * @param path the path to the file to create
   * @return the file
   * @throws IOException if an I/O error occurs
   */
  public Path createFile(Path path) throws IOException {
    return Files.createFile(path);
  }

  /**
   * Delegates to {@link Files#writeString(Path, CharSequence, OpenOption...)}.
   *
   * @param path the path to write to
   * @param csq  the character sequence to write
   * @throws IOException if an I/O error occurs
   */
  public void writeString(Path path, CharSequence csq) throws IOException {
    Files.writeString(path, csq);
  }

  /**
   * Delegates to {@link Files#readString(Path)}.
   *
   * @param path the path to read from
   * @return a string containing the file contents
   * @throws IOException if an I/O error occurs
   */
  public String readString(Path path) throws IOException {
    return Files.readString(path);
  }

  /**
   * Delegates to {@link Files#createDirectories(Path, FileAttribute...)}.
   *
   * @param dir the directory path
   * @throws IOException if an I/O error occurs
   */
  public void createDirectories(Path dir) throws IOException {
    Files.createDirectories(dir);
  }

  /**
   * Delegates to {@link Files#exists(Path, LinkOption...)}.
   *
   * @param path the path to the file to check for existence
   * @return true if the file exists, false otherwise
   */
  public boolean exists(Path path) {
    return Files.exists(path);
  }
}
