package pl.unforgiven.load.core.html;

/**
 * A representation of {@code <li>}.
 * @author miki
 * @since 2021-10-10
 */
class Li extends Tag {

  public Li() {
    super("li");
  }

  public final Li withNodes(Node... nodes) {
    for(Node node: nodes)
      this.addNode(node);
    return this;
  }

}
