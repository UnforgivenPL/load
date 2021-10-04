package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadData;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.path.Path;

import java.util.List;
import java.util.Map;

@Path("session")
public class HelloSessionPage implements LoadPage {

  private static final String VISITED_ATTRIBUTE = "visited";

  @Override
  @SuppressWarnings("squid:S2275")
  public Object get(List<String> path, Map<String, List<String>> parameters, LoadData data) {
    if(data.has(VISITED_ATTRIBUTE)) {
      final var counter = data.get(VISITED_ATTRIBUTE, Integer.class);
      data.put(VISITED_ATTRIBUTE, counter + 1);
      return String.format("This page has been visited %d times.", counter);
    }
    else {
      data.put(VISITED_ATTRIBUTE, 1);
      return "Welcome to this page. You have not visited it before. Enjoy your stay. Refresh this page.";
    }
  }
}
