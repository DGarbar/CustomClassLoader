import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyCustomClassLoader extends ClassLoader {

  Map<String, byte[]> fileHash = new HashMap<>();

  @Override
  public Class<?> findClass(String name) throws ClassNotFoundException {
    byte[] b = loadClassFromFile(name);
    fileHash.put(name, b);
    return defineClass(name, b, 0, b.length);
  }

  private byte[] loadClassFromFile(String fileName) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
        fileName.replace('.', File.separatorChar) + ".class");
    byte[] buffer;
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    int nextValue = 0;
    try {
      while ((nextValue = inputStream.read()) != -1) {
        byteStream.write(nextValue);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    buffer = byteStream.toByteArray();
    return buffer;
  }
}
