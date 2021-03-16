package pl.unforgiven.load.core.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Basic HTML element.
 * @author miki
 * @since 2021-03-06
 */
public class Tag implements NamedNode, HasNodes, HasAttributes {

  private final String name;

  private final Map<String, String> attributes = new HashMap<>();

  private final List<Node> nodes = new ArrayList<>();

  /**
   * Creates the tag with the given name.
   * @param name Name of the node. Must not be {@code null} nor empty.
   */
  public Tag(String name) {
    this.name = name;
  }

  @Override
  public final String getNodeName() {
    return this.getName();
  }

  /**
   * Same as {@link #getNodeName()}.
   * @return Name.
   * @see #getNodeName()
   */
  public String getName() {
    return name;
  }

  /**
   * Adds an empty attribute, overwriting its previous value.
   * @param name Attribute name.
   */
  public void addAttribute(String name) {
    this.attributes.put(name, "");
  }

  /**
   * Adds an attribute with given value, overwriting the previous one.
   * @param name Attribute name.
   * @param value Attribute value.
   */
  public void addAttribute(String name, String value) {
    this.attributes.put(name, value);
  }

  /**
   * Removes an attribute, if it existed.
   * @param name Attribute name.
   */
  public void removeAttribute(String name) {
    this.attributes.remove(name);
  }

  /**
   * Checks whether this object has an attribute with given name.
   * @param name Attribute name.
   * @return {@code true} when there is an attribute with exactly the same name, {@code false} otherwise.
   */
  public boolean hasAttribute(String name) {
    return this.attributes.containsKey(name);
  }

  @Override
  public String getAttribute(String name) {
    return this.attributes.get(name);
  }

  @Override
  public Set<String> getAttributes() {
    return Collections.unmodifiableSet(this.attributes.keySet());
  }

  @Override
  public List<Node> getNodes() {
    return Collections.unmodifiableList(nodes);
  }

  /**
   * Adds a node.
   * @param node Node to add.
   */
  public void addNode(Node node) {
    this.nodes.add(node);
  }

  /**
   * Removes a node.
   * @param node Node to remove.
   */
  public void removeNode(Node node) {
    this.nodes.remove(node);
  }

  @Override
  public String toString() {
    return "<"+this.getName()+" ("+this.getAttributes().size()+" attr) ("+this.getNodes().size()+" nodes) />";
  }
}
