package pl.unforgiven.load.core.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class with various path-related methods.
 * @author miki
 * @since 2021-10-10
 */
public class PathUtils {

  /**
   * Converts name-value pairs to a parameter map expected by various path-related operations.
   * @param nameValuePairs An array with alternating name and value. Must be of even length.
   * @return A map with parameters.
   * @throws IllegalArgumentException when the {@code nameValuePairs} array is not of even length.
   */
  public static Map<String, List<String>> toMap(String... nameValuePairs) {
    if(nameValuePairs.length % 2 != 0)
      throw new IllegalArgumentException("converting to a parameter map requires an even number of parameters");

    final var result = new HashMap<String, List<String>>();
    for(int zmp1 = 0; zmp1<nameValuePairs.length; zmp1 += 2)
      result.computeIfAbsent(nameValuePairs[zmp1], s -> new ArrayList<>()).add(nameValuePairs[zmp1+1]);
    return result;
  }

  private PathUtils() {
    // no instances
  }

}
