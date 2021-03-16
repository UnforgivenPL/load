package pl.unforgiven.load.core.html;

import java.util.Arrays;
import java.util.List;

/**
 * Basic {@code <html>} element.
 * @author miki
 * @since 2021-03-07
 */
public class Html implements Root, NamedNode, HasNodes {

  private final Tag head = new Tag("head");

  private final Tag body = new Tag("body");

  /**
   * The {@code <head>} element.
   * @return A {@link Tag} of the head.
   */
  public Tag getHead() {
    return head;
  }

  /**
   * The {@code <body>} element.
   * @return A {@link Tag} of the body.
   */
  public Tag getBody() {
    return body;
  }

  /**
   * Adds given nodes to the body and returns itself.
   * @param nodes Nodes to add.
   * @return This.
   */
  public final Html withBody(Node... nodes) {
    for (Node node : nodes)
      this.getBody().addNode(node);
    return this;
  }

  @Override
  public List<Node> getNodes() {
    return Arrays.asList(this.getHead(), this.getBody());
  }

  @Override
  public String getNodeName() {
    return "html";
  }
}
