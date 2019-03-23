import java.util.Map;
import net.sf.cglib.proxy.Enhancer;

public class CglibStarter {

  public static void main(String[] args) {
    StringInputParametersFormatter formatter = new StringInputParametersFormatter();
    Map<String, String> replacerMap = formatter.formatParameters(args);

    CglibStarter cglibStarter = new CglibStarter();
    System.out.println(cglibStarter.invokeProxy(replacerMap));
  }

  public String invokeProxy(Map<String, String> replacerMap){
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(TextService.class);
    enhancer.setCallback(new CglibMethodInterceptor(replacerMap));

    TextService textService = (TextService) enhancer.create();

    return textService.staticText();
  }
}
