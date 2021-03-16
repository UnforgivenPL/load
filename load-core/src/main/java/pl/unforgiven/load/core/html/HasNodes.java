package pl.unforgiven.load.core.html;

import java.util.List;

/**
 * Marker interface for everything that has {@link Node}s.
 * @author miki
 * @since 2021-03-12
 */
@FunctionalInterface
public interface HasNodes {

  /**
   * Gets all the nodes currently in the object.
   * Any changes to the returned list should not affect this object.
   * @return A non-{@code null} list.
   */
  List<Node> getNodes();

}
