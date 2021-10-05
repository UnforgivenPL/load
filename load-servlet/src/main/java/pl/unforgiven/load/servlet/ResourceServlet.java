package pl.unforgiven.load.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Servlet for static resources. Handles all requests inside {@code /static/*} (plus {@code /favicon.ico}.
 * It returns {@code 404 Not Found} when the path to the resource is illegal, the resource is not found or
 * there was an error in opening the resource. This is by design as to not expose inner structure of the filesystem.
 *
 * @author miki
 * @since 2021-10-04
 */
@WebServlet(value = {"/static/*", ResourceServlet.FAVICON_PATH}, loadOnStartup = 1)
public class ResourceServlet extends StaticServlet {

  @SuppressWarnings("squid:S1075") // uris should not be hardcoded, but this one has to be - it is the "favicon.ico" request
  public static final String FAVICON_PATH = "/favicon.ico";

  private static final Logger LOGGER = LoggerFactory.getLogger(ResourceServlet.class);

  // yes, I know it should say "sanitise"
  private static Optional<String> satanise(String string) {
    if(string == null || string.isBlank() || "/".equals(string)) {
      LOGGER.warn("empty resource path detected: [{}], resource not served", string);
      return Optional.empty();
    }
    else {
      final var name = URLDecoder.decode(string.substring(1), StandardCharsets.UTF_8);
      if ("/".equals(name) || name.contains("..") || name.contains("//") || name.contains(":") || name.contains("*") || name.contains("?")) {
        LOGGER.warn("illegal resource name detected: [{}], resource not served", name);
        return Optional.empty();
      }
      else return Optional.of("/"+name);
    }
  }

  @Override
  protected Optional<StaticResource> getStaticResource(HttpServletRequest request) {
    final var path = FAVICON_PATH.equals(request.getRequestURI()) ? "/icons" + FAVICON_PATH : request.getPathInfo();

    return satanise(path)
        .map(this.getClass().getClassLoader()::getResource)
        .flatMap(UrlResource::of);
  }

}
