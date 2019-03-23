import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMethodInterceptor implements MethodInterceptor {

  private Map<String, String> replacer;

  public CglibMethodInterceptor(
      Map<String, String> replacer) {
    this.replacer = replacer;
  }

  @Override
  public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy)
      throws Throwable {
    Object result = methodProxy.invokeSuper(o, args);
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
