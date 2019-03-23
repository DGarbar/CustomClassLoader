import java.lang.reflect.InvocationTargetException;

public class Starter {

  public static void main(String[] args) {
    TextServiceChangeTracker textServiceChangeTracker = new TextServiceChangeTracker();
    while (true) {
      try {
        Class<TextService> textServiceClass = (Class<TextService>) textServiceChangeTracker
            .getClass("TextService");
        Object text = textServiceClass.getMethod("staticText")
            .invoke(textServiceClass.getConstructors()[0].newInstance());
        System.out.println((String) text);
        Thread.sleep(6000);
      } catch (InvocationTargetException | InstantiationException | IllegalAccessException | InterruptedException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }

  }

}
