package pl.unforgiven.load.servlet.assets;

import pl.unforgiven.load.core.LoadAsset;
import pl.unforgiven.load.servlet.ReservedPaths;

import java.util.Objects;

/**
 * Definition of an asset that can be loaded as classpath resource.
 * @author miki
 * @since 2021-10-05
 */
public class ClasspathAsset implements LoadAsset {

  private final String name;

  /**
   * Creates an asset with given path.
   * @param path Path to the asset.
   */
  public ClasspathAsset(String path) {
    this.name = path;
  }

  /**
   * Checks if the asset is valid.
   * @return {@code true} when the class loader of this class can read the resource, {@code false} otherwise.
   */
  public boolean isValid() {
    return this.getClass().getClassLoader().getResource(this.name) != null;
  }

  @Override
  public String getPath() {
    return String.join("/", "", ReservedPaths.STATIC_PATH, this.name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClasspathAsset that = (ClasspathAsset) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override public String toString() {
    return "ClasspathAsset{" +
         name +
        '}';
  }
}
