package pl.unforgiven.load.core.html;

import pl.unforgiven.load.core.LoadAsset;

public class Link extends Tag {

  public static final String TARGET_ATTRIBUTE = "href";

  public Link() {
    super("a");
  }

  public Link withTarget(LoadAsset asset) {
    this.addAttribute(TARGET_ATTRIBUTE, asset.getPath());
    return this;
  }

  public Link withText(String text) {
    this.addNode(new Text().withContent(text));
    return this;
  }

}
