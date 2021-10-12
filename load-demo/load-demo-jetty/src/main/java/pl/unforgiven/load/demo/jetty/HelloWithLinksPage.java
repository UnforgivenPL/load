package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadData;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.assets.PageAsset;
import pl.unforgiven.load.core.html.Link;
import pl.unforgiven.load.core.html.Ul;
import pl.unforgiven.load.core.path.Path;

import java.util.List;
import java.util.Map;

/**
 * Shows various links.
 * @author miki
 * @since 2021-10-10
 */
@Path("links")
public class HelloWithLinksPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, List<String>> parameters, LoadData data) {
    return new Ul(
        new Link().withTarget(new PageAsset(HelloWorldPage.class)).withText("Click to see a basic text page."),
        new Link().withTarget(new PageAsset(HelloHtmlPage.class)).withText("Click to see a html page."),
        new Link().withTarget(new PageAsset(HelloImagesPage.class, "img", "gdansk")).withText("Click to see a page with a resource.")
    );
  }
}
