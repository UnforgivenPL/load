package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadData;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.path.Path;

import java.util.List;
import java.util.Map;

/**
 * A page displaying a simple "hello, world" for jetty.
 * @author miki
 * @since 2021-02-14
 */
@Path
public class HelloWorldPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, List<String>> parameters, LoadData data) {
    return "hello, dear world; request path was "+ String.join(", ", path);
  }
}
