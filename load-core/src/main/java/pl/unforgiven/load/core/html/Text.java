package pl.unforgiven.load.core.html;

import java.util.Objects;

/**
 * Represents an HTML text node, i.e. contents outside of any html element.
 * @author miki
 * @since 2021-03-12
 */
public class Text implements Node, HasStringContent {

  private String content;

  @Override
  public String getContent() {
    return content;
  }

  /**
   * Sets the content, overwriting previous one.
   * @param content Content.
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * Sets the content and returns itself.
   * @param content Content to set.
   * @return This.
   * @see #setContent(String)
   */
  public final Text withContent(String content) {
    this.setContent(content);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Text text = (Text) o;
    return Objects.equals(getContent(), text.getContent());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContent());
  }
}
