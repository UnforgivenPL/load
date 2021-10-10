package pl.unforgiven.load.core.html;

/**
 * Basic {@code <ul>} element.
 * @author miki
 * @since 2021-10-10
 */
public class Ul extends Tag {

  public Ul(Node... nodes) {
    super("ul");
    for(Node node: nodes)
      this.addNode(node);
  }

  @Override
  public void addNode(Node node) {
    super.addNode(new Li().withNodes(node));
  }
}
