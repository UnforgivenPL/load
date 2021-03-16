package pl.unforgiven.load.core.html;

import java.util.Set;

/**
 * Marker interface for objects that have attributes.
 * @author miki
 * @since 2021-03-16
 */
public interface HasAttributes {

  /**
   * The set of attributes. Changing this set does not affect this object.
   * @return A non-{@code null}, but possibly empty, set.
   */
  Set<String> getAttributes();

  /**
   * Returns the value of the given attribute.
   * @param name Name of the attribute.
   * @return {@code null} when there is no attribute of this name, otherwise the value of that attribute (which can be {@code null}).
   */
  String getAttribute(String name);

}
