import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class MyCustomClassLoader extends ClassLoader {

  Map<String, byte[]> fileHash = new HashMap<>();

  @Override
  public Class<?> findClass(String s) {
    byte[] bytes = new byte[0];
    try {
      bytes = loadClassData(s);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return defineClass(s, bytes, 0, bytes.length);

  }

  private byte[] loadClassData(String className) throws IOException, URISyntaxException {
    URL resource = getClass().getClassLoader().getResource(className + ".class");

    return Files.readAllBytes(Paths.get(resource.toURI()));
  }
}
