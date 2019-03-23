import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StringInputParametersFormatter {

  public Map<String, String> formatParameters(String... args) {
    if (args.length < 2) {
      return Collections.emptyMap();
    }
    HashMap<String, String> replacmentMap = new HashMap<>();
    int i = 0;
    while (i < args.length) {
      if (args[i].startsWith("-D") && i + 1 < args.length) {
        replacmentMap.put(createKey(args[i]), args[i + 1]);
        i += 2;
      } else {
        i++;
      }
    }
    return replacmentMap;
  }


  private String createKey(String key) {
    String newKey = key.substring(2).toLowerCase();
    return "${" + newKey + "}";
  }

}
