package pl.unforgiven.load.core.html;

/**
 * Marker interface for objects that have string content.
 * @author miki
 * @since 2021-03-16
 */
@FunctionalInterface
public interface HasStringContent {

  /**
   * The string content of this object.
   * @return A {@link String}. Can possibly be {@code null}.
   */
  String getContent();

}
