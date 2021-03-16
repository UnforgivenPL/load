package pl.unforgiven.load.core.html;

import java.util.Set;

public interface HasAttributes {

  Set<String> getAttributes();

  String getAttribute(String name);

}
