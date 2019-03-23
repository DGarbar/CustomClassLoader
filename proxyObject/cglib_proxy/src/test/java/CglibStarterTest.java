import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import org.junit.Test;

public class CglibStarterTest {

  CglibStarter cglibStarter = new CglibStarter();

  @Test
  public void resultProxyNotEqualsEntityResult() {
    HashMap<String, String> map = new HashMap<>();
    map.put("${port}", "8080");
    String result = cglibStarter.invokeProxy(map);
    assertNotEquals("server.port = ${port}", result);
  }

  @Test
  public void resultProxyReplaceParameter() {
    HashMap<String, String> map = new HashMap<>();
    map.put("${port}", "8080");
    map.put("${at}", "a");
    String result = cglibStarter.invokeProxy(map);
    assertEquals("server.port = 8080", result);
  }
}