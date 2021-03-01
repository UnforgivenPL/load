package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.path.Path;

import java.util.List;
import java.util.Map;

@Path("html")
public class HelloHtmlPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, List<String>> parameters) {
    return "<html><head><title>Hello, Load!</title><body><p>Hello, world.</p></body></html>";
  }
}
