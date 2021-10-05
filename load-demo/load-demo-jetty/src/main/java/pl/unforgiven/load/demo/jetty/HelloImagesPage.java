package pl.unforgiven.load.demo.jetty;

import pl.unforgiven.load.core.LoadData;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.assets.ExternalAsset;
import pl.unforgiven.load.core.html.Div;
import pl.unforgiven.load.core.html.Image;
import pl.unforgiven.load.core.html.Link;
import pl.unforgiven.load.core.html.Paragraph;
import pl.unforgiven.load.core.html.Text;
import pl.unforgiven.load.core.path.Path;
import pl.unforgiven.load.servlet.assets.ClasspathAsset;

import java.util.List;
import java.util.Map;

@Path({"image", "{img:[a-zA-Z0-9_-]+}"})
public class HelloImagesPage implements LoadPage {

  @Override
  public Object get(List<String> path, Map<String, List<String>> parameters, LoadData data) {
    final var imgs = parameters.get("img");
    final var img = imgs.isEmpty() ? "" : imgs.get(0);
    final var result = new Div().withNodes(
        new Paragraph().withContent("You are trying to display image with name ["+img+"].")
    );
    final var asset = new ClasspathAsset(img+".jpg");
    if(asset.isValid())
      result.withNodes(
          new Paragraph().withContent("The image has been found and can be seen below."),
          new Image().withSource(asset, "image of "+img),
          new Paragraph().withNodes(
              new Text().withContent("Picture taken by "),
              new Link().withTarget(new ExternalAsset("https://www.uneven-eyes.info")).withText("Miki"),
              new Text().withContent(".")
              )
      );
    else result.addNode(new Paragraph().withContent("Sadly, the image was not found in the classpath. Currently, only the image [gdansk] is supported."));
    return result;
  }
}
