import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyCustomClassLoader extends ClassLoader {

  Map<String, byte[]> fileHash = new HashMap<>();
  @Override
  public Class<?> findClass(String s) {
    byte[] bytes = new byte[0];
    try {
      bytes = loadClassData(s);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return defineClass(s, bytes, 0, bytes.length);

  }

  private byte[] loadClassData(String className) throws IOException {
    String path = getClass().getClassLoader().getResource(className + ".class").getPath();
    File f = new File( path);
    int size = (int) f.length();
    byte buff[] = new byte[size];
    FileInputStream fis = new FileInputStream(f);
    DataInputStream dis = new DataInputStream(fis);
    dis.readFully(buff);
    dis.close();
    return buff;
  }
}
