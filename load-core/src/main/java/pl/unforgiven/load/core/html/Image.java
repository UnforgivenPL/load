package pl.unforgiven.load.core.html;

import pl.unforgiven.load.core.LoadAsset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic {@code <img>} tag.
 * @author miki
 * @since 2021-10-05
 */
public class Image implements NamedNode, HasAttributes {

  private final Map<String, String> attributes = new HashMap<>(Map.of(
      "src", "",
      "img", ""
  ));

  public final Image withSource(LoadAsset resource) {
    return this.withSource(resource, resource.getPath());
  }

  public final Image withSource(LoadAsset resource, String altText) {
    this.attributes.put("src", resource.getPath());
    this.attributes.put("alt", altText);
    return this;
  }

  @Override
  public Set<String> getAttributes() {
    return this.attributes.keySet();
  }

  @Override
  public String getAttribute(String name) {
    return this.attributes.get(name);
  }

  @Override
  public String getNodeName() {
    return "img";
  }
}
