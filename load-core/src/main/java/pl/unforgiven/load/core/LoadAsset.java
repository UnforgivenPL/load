package pl.unforgiven.load.core;

/**
 * Marker interface for anything that resolves to a path.
 * @author miki
 * @since 2021-10-05
 *
 */
@FunctionalInterface
public interface LoadAsset {

  /**
   * Defines separator to join various path elements, if needed.
   */
  String SEPARATOR = "/";

  /**
   * Resolves the object to a path it can be accessed through by the server.
   * @return Path. Must never be {@code null}.
   */
  String getPath();

}
