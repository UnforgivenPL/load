package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.html.Paragraph;
import pl.unforgiven.load.core.path.Path;

import java.util.List;
import java.util.Map;

@Path("html")
public class HelloHtmlPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, List<String>> parameters) {
    return new Paragraph().withContent("Hello, world.");
  }
}
