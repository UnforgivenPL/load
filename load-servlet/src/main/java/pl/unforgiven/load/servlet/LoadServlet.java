package pl.unforgiven.load.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.unforgiven.load.core.LoadPage;
import pl.unforgiven.load.core.html.Html;
import pl.unforgiven.load.core.html.HtmlStringFactory;
import pl.unforgiven.load.core.html.Node;
import pl.unforgiven.load.core.html.Root;
import pl.unforgiven.load.core.path.PathMatch;
import pl.unforgiven.load.core.path.PathMatcher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet(value = "/", loadOnStartup = 1)
public class LoadServlet extends HttpServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoadServlet.class);

  private final HtmlStringFactory factory = new HtmlStringFactory();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      String requestUri = req.getRequestURI();
      if (requestUri.startsWith("/"))
        requestUri = requestUri.substring(1);

      LOGGER.info("path {}", requestUri);

      final PathMatcher matcher = new PathMatcher();

      final String[] pathSplit = requestUri.split("/");
      final Optional<PathMatch> perhapsPath = matcher.findMatchingPath(pathSplit);

      if (perhapsPath.isPresent()) {
        final PathMatch pathMatch = perhapsPath.get();
        final LoadPage page = pathMatch.getMatchingType().getConstructor().newInstance();

        final Object output = page.get(Arrays.asList(pathSplit), pathMatch.getParameters(), new HttpSessionDataWrapper(req.getSession()));
        if(output instanceof Node)
          this.processOutput((Node) output, resp);
        else resp.getOutputStream().println(output.toString());
      }
      else resp.getOutputStream().print("no LoadPage found for path: "+requestUri);
    } catch (IOException ioe) {
      LOGGER.error("could not write output", ioe);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      LOGGER.error("cannot create page due to {}", e.getMessage(), e);
    }
  }

  private void processOutput(Node node, HttpServletResponse response) throws IOException {
    if(!(node instanceof Root))
      node = new Html().withBody(node);
    response.getOutputStream().print(this.factory.build(node));
  }
}
