package pl.unforgiven.load.core;

import org.atteo.classindex.IndexSubclasses;

import java.util.List;
import java.util.Map;

/**
 * Basic page for load framework.
 * @author miki
 * @since 2021-02-14
 */
@IndexSubclasses
public interface LoadPage {

  default Object get(List<String> path, Map<String, List<String>> parameters, LoadData data) {
    return "not implemented";
  }

}
