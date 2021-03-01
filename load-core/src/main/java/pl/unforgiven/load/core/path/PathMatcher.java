package pl.unforgiven.load.core.path;

import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.unforgiven.load.core.LoadPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PathMatcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(PathMatcher.class);

  public Optional<PathMatch> findMatchingPath(String[] path) {
    final List<Class<? extends LoadPage>> pages = StreamSupport.stream(
        ClassIndex.getSubclasses(LoadPage.class).spliterator(), false
    ).collect(Collectors.toList());

    // when no pages, return nothing
    if(pages.isEmpty()) {
      LOGGER.warn("no implementations of LoadPage found, cannot continue");
      return Optional.empty();
    }

    // when there is one implementation NOT annotated with Path, return that
    else if(pages.size() == 1 && pages.get(0).getAnnotationsByType(Path.class).length == 0) {
      LOGGER.info("found one implementation of LoadPage not marked with @Path - returning it");
      return Optional.of(new PathMatch(pages.get(0), path, new HashMap<>()));
    }

    // there are more implementations, find all that are matching
    else {
      final List<PathMatch> matches = pages.stream()
          .map(page -> this.findMatchingPaths(page, path))
          .filter(paths -> !paths.isEmpty())
          .flatMap(List::stream)
          .collect(Collectors.toList());
      // if no matches
      if(matches.isEmpty()) {
        LOGGER.warn("no implementations of LoadPage marked with matching @Path found, cannot continue to {}", (Object)path);
        return Optional.empty();
      }
      // if one, return it
      else if(matches.size() == 1)
        return Optional.of(matches.get(0));
      // otherwise, attempt to sort and return first
      else return matches.stream().min(this::pathMatchComparator);
    }

  }

  private int pathMatchComparator(PathMatch first, PathMatch second) {
    final int result = Integer.compare(first.getMatchingAnnotation().priority(), second.getMatchingAnnotation().priority());
    return result == 0 ? first.getMatchingType().getName().compareTo(second.getMatchingType().getName()) : result;
  }

  private List<PathMatch> findMatchingPaths(Class<? extends LoadPage> page, String[] path) {
    return Stream.of(page.getAnnotationsByType(Path.class))
        .map(annotation -> this.getPathMatch(page, annotation, path))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }

  private Optional<PathMatch> getPathMatch(Class<? extends LoadPage> page, Path annotation, String[] path) {
    // first check: if path definition length is more than path, no match
    if(annotation.value().length > path.length || annotation.value().length == 0)
      return Optional.empty();

    // there can be only one path that ends with *, and it must have a named parameter in it
    int repeatedIndex = -1;
    int index = 0;
    final List<String> parts = new ArrayList<>();
    while(parts.size() < path.length && index < annotation.value().length) {
      final String part = annotation.value()[index];

      // if found a repeating element, keep adding it without the *
      if(repeatedIndex == -1 && part.endsWith("*") && part.indexOf('{') < part.indexOf('}')) {
        repeatedIndex = index;
        for(int zmp1=0; zmp1<path.length-annotation.value().length+1; zmp1++)
          parts.add(part.substring(0, part.length()-1));
      }
      else parts.add(part);
      index++;
    }

    // now, both parts and path must have the same size to have a chance to match
    if(parts.size() != path.length)
      return Optional.empty();

    final Map<String, List<String>> parameters = new HashMap<>();
    boolean matching = true;
    int zmp1=0;
    while(matching && zmp1 < path.length)
      matching = this.isMatching(parts.get(zmp1), path[zmp1++], parameters);

    if(!matching) return Optional.empty();
    else return Optional.of(new PathMatch(page, annotation, Arrays.asList(path), parameters));

  }

  /**
   * Checks whether a given path element matches a pattern, updating parameters if it does.
   * @param pattern Pattern to match. May contain at most one {@code {paramName:regexp}}. Must not contain {@code *} nor {@code /}.
   * @param pathElement String to check against the pattern.
   * @param parameters Map with parameters. It will be updated if needed.
   * @return Whether or not given path element matches the given pattern.
   */
  public boolean isMatching(String pattern, String pathElement, Map<String, List<String>> parameters) {
    // if there are no parameter definitions, paths must be equal
    final int start = pattern.indexOf('{');
    final int end = pattern.indexOf('}');
    if(start >= end)
      return pattern.equals(pathElement);

    // start (before {) must match
    // // end (after }) must also match

    if(start >= pathElement.length() || (start > 0 && !pattern.substring(0, start).equals(pathElement.substring(0, start))) ||
        (end < pattern.length()-1 && !pattern.substring(end+1).equals(pathElement.substring(pathElement.length()-pattern.length()+end+1))))
      return false;

    // figure out the parameter, but without brackets
    final String paramDefinition = pattern.substring(start+1, end);

    // it may happen that the end index is further than the start index, hence the max here
    final String correspondingPathElement = pathElement.substring(start, Math.max(pathElement.length()-(pattern.length()-end)+1, start));

    // see if there is regular expression
    final int colonIndex = paramDefinition.indexOf(':');
    final boolean noColon = colonIndex == -1;

    // if not, everything between start and path.length - pattern.length + end is stored as the parameter
    // if yes, then regular expression must match
    if(noColon || correspondingPathElement.matches(paramDefinition.substring(colonIndex+1))) {
      parameters.computeIfAbsent(
          noColon ? paramDefinition : paramDefinition.substring(0, colonIndex),
          key -> new ArrayList<>()).add(correspondingPathElement);
      return true;
    }
    else return false;
  }

}
