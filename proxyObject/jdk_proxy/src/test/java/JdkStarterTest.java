import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;

public class JdkStarterTest {
  JdkStarter jdkStarter = new JdkStarter();

  @Test
  public void resultProxyNotEqualsEntityResult() {
    HashMap<String, String> map = new HashMap<>();
    map.put("${port}", "8080");
    String result = jdkStarter.invokeProxy(map);
    assertNotEquals("server.port = ${port}", result);
  }

  @Test
  public void resultProxyReplaceParameter() {
    HashMap<String, String> map = new HashMap<>();
    map.put("${port}", "8080");
    map.put("${at}", "a");
    String result = jdkStarter.invokeProxy(map);
    assertEquals("server.port = 8080", result);
  }
}