import java.lang.reflect.Proxy;
import java.util.Map;

public class JdkStarter {

  public static void main(String[] args) {
    StringInputParametersFormatter formater = new StringInputParametersFormatter();
    Map<String, String> replacerMap = formater.formatParameters(args);
    JdkStarter jdkStarter = new JdkStarter();
    System.out.println(jdkStarter.invokeProxy(replacerMap));
  }


  public String invokeProxy(Map<String, String> replacerMap) {
    EnvVariableProxyReplacer proxy = new EnvVariableProxyReplacer(new TextService(), replacerMap);

    TextReturnable myProxy = (TextReturnable) Proxy.newProxyInstance(
        TextService.class.getClassLoader(),
        new Class[]{TextReturnable.class},
        proxy);

    return myProxy.staticText();
  }
}
