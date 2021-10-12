package pl.unforgiven.load.core.path;

import org.atteo.classindex.ClassIndex;
import pl.unforgiven.load.core.LoadPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Unwraps path into strings.
 * @author miki
 * @since 2021-10-10
 */
public final class PathFinder {

  /**
   * Finds the first path that matches given parameters. It goes through all available {@link LoadPage}s.
   * @param parameters Parameters.
   * @return A matching path that can be navigated to.
   */
  public Optional<String[]> findPath(Map<String, List<String>> parameters) {
    return StreamSupport.stream(
        ClassIndex.getSubclasses(LoadPage.class).spliterator(), false
      ).map(page -> this.findPath(page, parameters))
        .filter(Optional::isPresent)
        .findFirst()
        .map(Optional::get);
  }

  /**
   * Finds the path that matches parameters. In case multiple paths match, one with the highest priority is returned.
   * @param page Page to look paths for.
   * @param parameters Parameters. May never be {@code null}, but can be empty.
   * @return A matching path that can be navigated to.
   */
  public Optional<String[]> findPath(Class<? extends LoadPage> page, Map<String, List<String>> parameters) {
    return Stream.of(page.getAnnotationsByType(Path.class))
        .sorted(Comparator.comparingInt(Path::priority))
        .map(path -> this.findPath(parameters, path.value()))
        .filter(Optional::isPresent)
        .findFirst()
        .map(Optional::get);
  }

  /**
   * Adds all parameters and their regexes from given path element to the given map.
   * @param parameters Parameters so far.
   * @param element Element to scan.
   */
  private void updateParameters(Map<String, PathReplacement> parameters, String element, int pathIndex) {
    final var start = element.indexOf('{');
    final var end = element.indexOf('}');
    // must figure out the name and the name must be at least one character long
    if (start <= end-1) {
      int colon = element.indexOf(':');
      final String regexp;
      if (colon == -1 || colon < start || end < colon) {
        regexp = ".*";
        colon = end;
      }
      else
        regexp = element.substring(colon+1, end);
      final String paramName = element.substring(start+1, colon);
      final boolean isRepeating = element.endsWith("}*");
      parameters.put(paramName, new PathReplacement(paramName, regexp, pathIndex, start, end, isRepeating));
    }
  }

  /**
   * Goes through the path and collects all parameters with their regular expressions.
   * @param path Path.
   * @return Map of parameters and their regexes. Can be empty.
   */
  private Map<String, PathReplacement> getParameters(String... path) {
    final Map<String, PathReplacement> result = new HashMap<>();
    for(int zmp1=0; zmp1<path.length; zmp1++)
      this.updateParameters(result, path[zmp1], zmp1);
    return result;
  }

  private boolean valueMatches(List<String> value, String regexp) {
    return value.get(0).matches(regexp);
  }

  private String[] replacePath(Map<String, PathReplacement> replacementMap, Map<String, List<String>> values, String... path) {
    final List<List<String>> mapped = new ArrayList<>(path.length);
    for (String s : path)
      mapped.add(Collections.singletonList(s));
    replacementMap.values().forEach(replacement -> {
      final int index = replacement.getPathIndex();
      final String element = path[index];
      final String suffix = replacement.isRepeating() ? "" : element.substring(replacement.getEndIndex()+1);
      mapped.set(index,
          values.get(replacement.getParameterName()).stream()
              .map(value -> element.substring(0, replacement.getStartIndex()) + value +
                  suffix)
              .collect(Collectors.toList()));
        });

    return mapped.stream().flatMap(List::stream).toArray(String[]::new);
  }

  Optional<String[]> findPath(Map<String, List<String>> params, String... path) {
    // 1. locate all parameters
    final var regexps = this.getParameters(path);
    // 2. all found parameters must be present in the parameter map
    //    and regular expressions must match with given values
    if(regexps.keySet().equals(params.keySet()) &&
        regexps.entrySet().stream()
            .allMatch(regexpEntry -> this.valueMatches(params.get(regexpEntry.getKey()), regexpEntry.getValue().getRegexp())))
      return Optional.of(this.replacePath(regexps, params, path));
    else return Optional.empty();
  }

}
