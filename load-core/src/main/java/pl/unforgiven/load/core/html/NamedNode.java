package pl.unforgiven.load.core.html;

/**
 * Marker interface for {@link Node}s that have a name.
 * @author miki
 * @since 2021-03-16
 */
@FunctionalInterface
public interface NamedNode extends Node {

  /**
   * The name of the node.
   * @return A non-{@code null} and non-empty node name.
   */
  String getNodeName();

}
