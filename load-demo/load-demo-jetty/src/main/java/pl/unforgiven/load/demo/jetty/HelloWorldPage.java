package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadPage;

import java.util.List;
import java.util.Map;

/**
 * A page displaying a simple "hello, world" for jetty.
 * @author miki
 * @since 2021-02-14
 */
public class HelloWorldPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, String[]> parameters) {
    return "hello, dear world";
  }
}
