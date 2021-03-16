package pl.unforgiven.load.core.html;

import java.util.Arrays;
import java.util.List;

/**
 * Basic HTML element.
 * @author miki
 * @since 2021-03-07
 */
public class Html implements NamedNode, HasNodes {

  private final Head head = new Head();

  private final Body body = new Body();

  public Head getHead() {
    return head;
  }

  public Body getBody() {
    return body;
  }

  public Html withBody(Node... nodes) {
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
