package pl.unforgiven.load.core.html;

public class Paragraph extends Tag {

  public Paragraph() {
    super("p");
  }

  public final Paragraph withContent(String content) {
    this.addNode(new Text().withContent(content));
    return this;
  }

}
