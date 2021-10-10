package pl.unforgiven.load.core.path;

import java.util.Objects;

/**
 * Stores information about where to put replacement when unwrapping paths.
 * @author miki
 * @since 2021-10-09
 */
public final class PathReplacement {

  private final String parameterName;

  private final String regexp;

  private final int pathIndex;

  private final int startIndex;

  private final int endIndex;

  private final boolean repeating;

  public PathReplacement(String parameterName, String regexp, int pathIndex, int startIndex, int endIndex, boolean repeating) {
    this.parameterName = parameterName;
    this.regexp = regexp;
    this.pathIndex = pathIndex;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.repeating = repeating;
  }

  public boolean isRepeating() {
    return repeating;
  }

  public int getPathIndex() {
    return pathIndex;
  }

  public String getParameterName() {
    return parameterName;
  }

  public String getRegexp() {
    return regexp;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
    return endIndex;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PathReplacement that = (PathReplacement) o;
    return getPathIndex() == that.getPathIndex() && getStartIndex() == that.getStartIndex() && getEndIndex() == that.getEndIndex() && isRepeating() == that.isRepeating() && getParameterName().equals(that.getParameterName()) && getRegexp().equals(that.getRegexp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getParameterName(), getRegexp(), getPathIndex(), getStartIndex(), getEndIndex(), isRepeating());
  }

  @Override
  public String toString() {
    return "PathReplacement{" +
        "parameterName='" + parameterName + '\'' +
        ", regexp='" + regexp + '\'' +
        ", pathIndex=" + pathIndex +
        ", startIndex=" + startIndex +
        ", endIndex=" + endIndex +
        ", repeating=" + repeating +
        '}';
  }
}
