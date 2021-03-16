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

  public Tag(String name) {
    this.name = name;
  }

  @Override
  public final String getNodeName() {
    return this.getName();
  }

  public String getName() {
    return name;
  }

  public void addAttribute(String name) {
    this.attributes.put(name, "");
  }

  public void addAttribute(String name, String value) {
    this.attributes.put(name, value);
  }

  public void removeAttribute(String name) {
    this.attributes.remove(name);
  }

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

  public void addNode(Node node) {
    this.nodes.add(node);
  }

  public void removeNode(Node node) {
    this.nodes.remove(node);
  }

  @Override
  public String toString() {
    return "<"+this.getName()+" ("+this.getAttributes().size()+" attr) ("+this.getNodes().size()+" nodes) />";
  }
}
