package pl.unforgiven.load.core.html;

/**
 * Basic {@code <div>} element.
 * @author miki
 * @since 2021-10-05
 */
public class Div extends Tag {

  public Div(Node... nodes) {
    super("div");
    this.withNodes(nodes);
  }

  public final Div withNodes(Node... nodes) {
    for(Node node: nodes)
      this.addNode(node);
    return this;
  }

}
