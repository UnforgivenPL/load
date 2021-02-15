package pl.unforgiven.load.servlet;

import org.atteo.classindex.ClassIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.unforgiven.load.core.LoadPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet(value = "/", loadOnStartup = 1)
public class LoadServlet extends HttpServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoadServlet.class);

  private final List<LoadPage> pages = new ArrayList<>();

  public LoadServlet() {
    ClassIndex.getSubclasses(LoadPage.class).forEach(pageClass -> {
      try {
        this.pages.add(pageClass.getConstructor().newInstance());
        LOGGER.info("successfully registered {}", pageClass.getSimpleName());
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        LOGGER.error("cannot create page {} due to {}", pageClass.getSimpleName(), e.getMessage(), e);
      }
    });
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if(this.pages.size() != 1)
      resp.getOutputStream().println("No page or too many pages registered for the request. Cannot continue.");
    else {
      LOGGER.info("parameters {}", req.getParameterMap());
      LOGGER.info("path {}", req.getRequestURI());
      resp.getOutputStream().println(this.pages.get(0).get(Collections.emptyList(), req.getParameterMap()).toString());
    }
  }
}