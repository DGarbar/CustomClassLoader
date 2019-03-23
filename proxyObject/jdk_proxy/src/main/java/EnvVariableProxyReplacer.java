import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

public class EnvVariableProxyReplacer implements InvocationHandler {

  private Object textService;
  private Map<String, String> replacer;

  public EnvVariableProxyReplacer(Object textService,
      Map<String, String> replacer) {
    this.textService = textService;
    this.replacer = replacer;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object result = method.invoke(textService, args);
    if (result instanceof String) {
      return formatString((String) result);
    }
    return result;
  }

  private String formatString(String result) {
    for (Entry<String, String> replaceEntry : replacer.entrySet()) {
      result = result.replace(replaceEntry.getKey(), replaceEntry.getValue());
    }
    return result;
  }

}
