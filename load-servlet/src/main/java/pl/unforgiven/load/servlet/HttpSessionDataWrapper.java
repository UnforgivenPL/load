package pl.unforgiven.load.servlet;

import pl.unforgiven.load.core.LoadData;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Wrapper for {@link HttpSession} to work with {@link LoadData}.
 * @author miki
 * @since 2021-09-21
 */
class HttpSessionDataWrapper implements LoadData {

  private final HttpSession session;

  HttpSessionDataWrapper(HttpSession session) {
    this.session = session;
  }

  @Override
  public String get(String name) {
    return Optional.ofNullable(this.session.getAttribute(name))
        .map(Object::toString)
        .orElse(null);
  }

  @Override
  public <T> T get(String name, Class<T> type) {
    return Optional.ofNullable(session.getAttribute(name))
        .filter(type::isInstance)
        .map(type::cast)
        .orElse(null);
  }

  @Override
  public void put(String name, Object data) {
    this.session.setAttribute(name, data);
  }

  @Override
  public Object remove(String name) {
    final var result = this.session.getAttribute(name);
    this.session.removeAttribute(name);
    return result;
  }

  @Override
  public void clear() {
    final var attributeNames = this.session.getAttributeNames();
    while(attributeNames.hasMoreElements())
      this.session.removeAttribute(attributeNames.nextElement());
  }

  @Override
  public boolean has(String name) {
    return this.session.getAttribute(name) != null;
  }
}
