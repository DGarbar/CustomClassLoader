import java.lang.reflect.InvocationTargetException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class Starter {

  public static void main(String[] args) {
    JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
    MyCustomClassLoader myCustomClassLoader = new MyCustomClassLoader();

    while (true) {
      try {
        myCustomClassLoader = new MyCustomClassLoader();
        Class<TextService> textServiceClass = (Class<TextService>) myCustomClassLoader
            .findClass("TextService");
        Object textService = textServiceClass.getMethod("staticText")
            .invoke(textServiceClass.getConstructors()[0].newInstance());
        Thread.sleep(6000);
        System.out.println((String) textService);
      } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | InterruptedException | NoSuchMethodException e) {
        e.printStackTrace();
      }
    }

  }

}
