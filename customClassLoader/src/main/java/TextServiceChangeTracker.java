import java.util.Arrays;

public class TextServiceChangeTracker {

  private MyCustomClassLoader currentClassLoader = new MyCustomClassLoader();
  private byte[] classByte;


  public Class<?> getClass(String className) {
    compareCurrentFileClass(className);
    return currentClassLoader.getClass(className);
  }

  private boolean compareCurrentFileClass(String className) {
    byte[] currentClassBytes = currentClassLoader.loadClassData(className);
    boolean change = false;

    if (classByte == null) {
      classByte = currentClassBytes;
      return false;
    }

    if (!Arrays.equals(currentClassBytes, classByte)) {
      classByte = currentClassBytes;
      currentClassLoader = new MyCustomClassLoader();
      change = true;
    }
    return change;
  }


}
