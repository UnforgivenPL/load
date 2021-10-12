package pl.unforgiven.load.core.path;

import pl.unforgiven.load.core.LoadPage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specialised {@link IllegalArgumentException} for cases where path cannot be found.
 * @author miki
 * @since 2021-10-10
 */
public class PathNotFoundException extends IllegalArgumentException {

  private final Class<? extends LoadPage> page;
  private final Map<String, List<String>> parameters = new HashMap<>();

  public PathNotFoundException(Class<? extends LoadPage> page) {
    this(page, Collections.emptyMap());
  }

  public PathNotFoundException(Class<? extends LoadPage> page, Map<String, List<String>> parameters) {
    this.page = page;
    this.parameters.putAll(parameters);
  }

  public Class<? extends LoadPage> getPage() {
    return page;
  }

  public Map<String, List<String>> getParameters() {
    return parameters;
  }
}
