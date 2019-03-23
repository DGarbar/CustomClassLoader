import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyCustomClassLoader extends ClassLoader {

  Map<String, Class<?>> map = new HashMap<>();

  public Class<?> getClass(String s) {
    Class<?> aClass;
    try {
      aClass = findClass(s);
    } catch (ClassNotFoundException e) {
      aClass = loadMyClass(s);
    }
    return aClass;
  }

  @Override
  public Class<?> findClass(String s) throws ClassNotFoundException {
    Class<?> aClass = map.get(s);
    if (aClass == null) {
      throw new ClassNotFoundException();
    }
    return aClass;
  }


  public Class<?> loadMyClass(String name) {
    byte[] bytes = loadClassData(name);
    return loadClass(name, bytes);
  }

  public Class<?> loadClass(String name, byte[] classBytes) {
    Class<?> aClass = null;
    try {
      System.out.println(name);
      aClass = defineClass(name, classBytes, 0, classBytes.length);
      map.put(name, aClass);
    } catch (Exception classFormatError) {
      classFormatError.printStackTrace();
    }

    return aClass;
  }

  public byte[] loadClassData(String className) {
    try {
      URL resource = getClass().getClassLoader().getResource(className + ".class");
      System.out.println("srsesrsar           " + resource);
      Objects.requireNonNull(resource);
      return Files.readAllBytes(Paths.get(resource.toURI()));
    } catch (IOException | URISyntaxException e) {
      throw new IllegalStateException();
    }
  }
}
