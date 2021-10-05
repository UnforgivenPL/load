package pl.unforgiven.load.core.html;

/**
 * Basic {@code <p>} element.
 * @author miki
 * @since 2021-03-16
 */
public class Paragraph extends Tag {

  public Paragraph() {
    super("p");
  }

  /**
   * Adds {@link Text} node with the given text and returns itself.
   * @param content Content.
   * @return This.
   */
  public final Paragraph withContent(String content) {
    this.addNode(new Text().withContent(content));
    return this;
  }

  public final Paragraph withNodes(Node... nodes) {
    for(Node node: nodes)
      this.addNode(node);
    return this;
  }

}
