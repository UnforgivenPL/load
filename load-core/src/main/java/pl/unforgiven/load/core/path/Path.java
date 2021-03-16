package pl.unforgiven.load.core.path;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a path leading to a page.
 * @author miki
 * @since 2021-02-25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Paths.class)
@Documented
public @interface Path {

  /**
   * Defines path. This supports {@code {parameterName:patternToMatch}}.
   *
   * No element of path may contain {@code /} and curly brackets {@code { }} are permitted only to encapsulate parameters (both must be present).
   *
   * No element of path may contain {@code *}, except at most one element with parameter name that ends with it, or in the parameter's pattern to match.
   * In such case all matching continuous path elements will be placed inside that variable.
   *
   * If path element contains parameter name without the pattern to match, pattern is considered to be {@code .*} - so the biggest matching set.
   *
   * Parameter name must not contain {@code :} or {@code }}.
   *
   * Pattern to match must not contain {@code }}.
   *
   * Invalid paths are ignored during pattern matching (and may also log a warning).
   */
  String[] value() default {""};

  /**
   * Defines path priority. Lower priority means higher priority, i.e. in cases of multiple matching path with lower priority is selected.
   * In case of equal priority the annotated class FQN will be taken and compared.
   */
  int priority() default Integer.MAX_VALUE;

}
