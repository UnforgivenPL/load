package pl.unforgiven.load.core.path;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Wrapper for pages that have more than one {@link Path}.
 * @author miki
 * @since 2021-02-25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Paths {

  Path[] value();

}
