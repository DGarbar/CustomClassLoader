import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

public class StringInputParametersFormatterTest {

  StringInputParametersFormatter stringInputParametersFormatter = new StringInputParametersFormatter();

  @org.junit.Test
  public void oneReplacementTest() {
    String[] str = {"-Dport", "8080"};
    HashMap<String, String> expected = new HashMap<>();
    expected.put("${port}", "8080");
    Map<String, String> result = stringInputParametersFormatter.formatParameters(str);
    assertEquals(expected,result);
  }

  @org.junit.Test
  public void replacementWithSpamParameters() {
    String[] str = {"jaca","-Dport", "8080","jaca","a"};
    HashMap<String, String> expected = new HashMap<>();
    expected.put("${port}", "8080");
    Map<String, String> result = stringInputParametersFormatter.formatParameters(str);
    assertEquals(expected,result);
  }

  @org.junit.Test
  public void emptyReplacement() {
    String[] str = {"-a", "8080"};
    HashMap<String, String> expected = new HashMap<>();
    Map<String, String> result = stringInputParametersFormatter.formatParameters(str);
    assertEquals(expected,result);
  }


}