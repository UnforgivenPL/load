package pl.unforgiven.load.core.assets;

import pl.unforgiven.load.core.LoadAsset;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.path.PathFinder;
import pl.unforgiven.load.core.path.PathNotFoundException;
import pl.unforgiven.load.core.path.PathUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a {@link LoadPage} asset. Useful e.g. in links.
 * @author miki
 * @since 2021-10-10
 */
public class PageAsset implements LoadAsset {

  private final String path;

  public PageAsset(Class<? extends LoadPage> page, String... parameters) {
    this(page, PathUtils.toMap(parameters));
  }

  public PageAsset(Class<? extends LoadPage> page) {
    this(page, Collections.emptyMap());
  }

  public PageAsset(Class<? extends LoadPage> page, Map<String, List<String>> parameters) {
    final PathFinder finder = new PathFinder();
    this.path = String.join(SEPARATOR, finder.findPath(page, parameters).orElseThrow(
        () -> new PathNotFoundException(page, parameters)
    ))+SEPARATOR;
  }

  @Override
  public String getPath() {
    return this.path;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PageAsset pageAsset = (PageAsset) o;
    return Objects.equals(getPath(), pageAsset.getPath());
  }

  @Override public int hashCode() {
    return Objects.hash(getPath());
  }

  @Override public String toString() {
    return "PageAsset{" +
        "path='" + path + '\'' +
        '}';
  }
}
