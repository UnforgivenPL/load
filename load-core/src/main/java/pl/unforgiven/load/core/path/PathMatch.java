package pl.unforgiven.load.core.path;

import pl.unforgiven.load.core.LoadPage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Wraps path matching results.
 * @author miki
 * @since 2021-02-25
 */
public final class PathMatch {

  private final Path matchingAnnotation;

  private final Class<? extends LoadPage> matchingType;

  private final List<String> matchingPath;

  private final Map<String, List<String>> parameters;

  public PathMatch(Class<? extends LoadPage> matchingType, String[] matchingPath, Map<String, List<String>> parameters) {
    this(matchingType, null, Arrays.asList(matchingPath), parameters);
  }

  public PathMatch(Class<? extends LoadPage> matchingType, Path matchingAnnotation, List<String> matchingPath, Map<String, List<String>> parameters) {
    this.matchingType = matchingType;
    this.matchingAnnotation = matchingAnnotation;
    this.matchingPath = matchingPath;
    this.parameters = parameters;
  }

  public Class<? extends LoadPage> getMatchingType() {
    return matchingType;
  }

  public List<String> getMatchingPath() {
    return matchingPath;
  }

  public Map<String, List<String>> getParameters() {
    return parameters;
  }

  public Path getMatchingAnnotation() {
    return matchingAnnotation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathMatch pathMatch = (PathMatch) o;
    return Objects.equals(getMatchingAnnotation(), pathMatch.getMatchingAnnotation()) && getMatchingType().equals(pathMatch.getMatchingType()) && getMatchingPath().equals(pathMatch.getMatchingPath()) && getParameters().equals(pathMatch.getParameters());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMatchingAnnotation(), getMatchingType(), getMatchingPath(), getParameters());
  }
}
