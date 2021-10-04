package pl.unforgiven.load.core;

import java.util.Map;

/**
 * Basic interface for named (and typed) data.
 * @author miki
 * @since 2021-09-21
 */
public interface LoadData {

  boolean has(String name);

  String get(String name);

  <T> T get(String name, Class<T> type);

  void put(String name, Object data);

  Object remove(String name);

  void clear();

  default void putAll(Map<String, Object> data) {
    data.forEach(this::put);
  }

}
