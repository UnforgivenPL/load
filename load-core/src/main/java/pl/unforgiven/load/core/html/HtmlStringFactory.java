package pl.unforgiven.load.core.html;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Builds an HTML String from a given Node.
 * @author miki
 * @since 2021-03-15
 */
public class HtmlStringFactory {

  private static String escape(String string) {
    return string.replace("&", "&apos;")
        .replace("<", "&lt;").replace(">", "&gt;")
        .replace("\"", "&quot;").replace("'", "&apos;")
        ;
  }

  private void buildAttributes(HasAttributes node, StringBuilder result) {
    if(!node.getAttributes().isEmpty()) {
      result.append(" ").append(node.getAttributes().stream().map(attr -> {
        final String value = node.getAttribute(attr);
        return value == null ? attr : attr + " = \"" + escape(value) + "\"";
      }).collect(Collectors.joining(" ")));
    }
  }

  private void build(Node node, StringBuilder result) {
    // yeah, this method should be refactored
    if(node instanceof NamedNode) {
      result.append("<").append(((NamedNode) node).getNodeName());
      if(node instanceof HasAttributes)
        this.buildAttributes((HasAttributes) node, result);
      if(node instanceof HasNodes) {
        final List<Node> nodes = ((HasNodes) node).getNodes();
        if(!nodes.isEmpty()) {
          result.append(">");
          nodes.forEach(n -> this.build(n, result));
          result.append("</").append(((NamedNode) node).getNodeName()).append(">");
        }
        else result.append("/>");
      }
      else if(node instanceof HasStringContent)
        result.append(">").append(((HasStringContent) node).getContent()).append("</").append(((NamedNode) node).getNodeName()).append(">");
      else result.append("/>");
    }
    else if(node instanceof HasStringContent)
      result.append(((HasStringContent) node).getContent());
  }

  /**
   * Builds HTML out of a {@link Node}.
   * @param node Node to build HTML from. Must never be {@code null}.
   * @return An HTML string.
   */
  public String build(Node node) {
    final StringBuilder result = new StringBuilder();
    this.build(node, result);
    return result.toString();
  }

}
