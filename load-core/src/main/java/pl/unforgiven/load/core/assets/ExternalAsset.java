package pl.unforgiven.load.core.assets;

import pl.unforgiven.load.core.LoadAsset;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Represents a {@link LoadAsset} that is located elsewhere.
 * @author miki
 * @since 2021-10-05
 */
public class ExternalAsset implements LoadAsset {

  private final URL target;

  /**
   * Creates the asset from a given url.
   * @param url Url to create asset for. It will be parsed via {@link URL#URL(String)}, rethrowing {@link MalformedURLException} as {@link IllegalArgumentException}.
   * @throws IllegalArgumentException when the url string is an incorrect URL
   */
  public ExternalAsset(String url) {
    try {
      this.target = new URL(url);
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(String.format("the url [%s]looks incorrect, cannot create external asset out of it", url), e);
    }
  }

  @Override
  public String getPath() {
    return this.target.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExternalAsset that = (ExternalAsset) o;
    return Objects.equals(target, that.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target);
  }

  @Override public String toString() {
    return "ExternalAsset{" +
        target +
        '}';
  }
}
