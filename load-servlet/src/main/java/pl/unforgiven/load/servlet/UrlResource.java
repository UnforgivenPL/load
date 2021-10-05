package pl.unforgiven.load.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * Straightforward implementation of {@link StaticResource} to work with URL resources.
 * @author miki
 * @since 2021-10-05
 */
public class UrlResource implements StaticResource {

  /**
   * Creates the {@link UrlResource} if everything went fine.
   * @param url Url to create resource for.
   * @return The resource, if successful. All exceptions are eaten, but logged.
   */
  public static Optional<UrlResource> of(URL url) {
    try {
      return Optional.of(new UrlResource(url));
    }
    catch (IOException ioe) {
      LOGGER.error("could not create UrlResource out of {}", url, ioe);
      return Optional.empty();
    }
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(UrlResource.class);

  private final String name;
  private final long lastModified;
  private final long size;
  private final InputStream stream;

  /**
   * Creates the resource.
   * @param url Url to create the resource for.
   * @throws IOException when anything goes wrong.
   */
  public UrlResource(URL url) throws IOException {
    this.name = url.getFile();
    final var connection = url.openConnection();
    this.lastModified = connection.getLastModified();
    this.size = connection.getContentLength();
    this.stream = connection.getInputStream();
  }

  @Override
  public String getFileName() {
    return this.name;
  }

  @Override
  public long getLastModified() {
    return this.lastModified;
  }

  @Override
  public long getContentLength() {
    return this.size;
  }

  @Override
  public InputStream getInputStream() {
    return this.stream;
  }
}
