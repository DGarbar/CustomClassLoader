import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileTracking {

  private Path filePath;
  private List<String> hash;

  private FileTracking(Path filePath) {
    verify(filePath);
    this.filePath = filePath;
    setHash();
  }

  public static FileTracking trackFile(Path path) {
    return new FileTracking(path);
  }

  private void verify(Path path) {
    if (!Files.isRegularFile(path) || !path.getFileName().toString().endsWith(".java")) {
      throw new IllegalArgumentException();
    }
  }

  public void setHash() {
    hash = getCurrentHash();
  }

  private List<String> getCurrentHash() {
    try {
      return Files.readAllLines(filePath);
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  private boolean isChanged() {
    return !getCurrentHash().equals(hash);
  }
}
