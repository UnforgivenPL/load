package pl.unforgiven.load.core.path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class PathMatcherTest {

  private final PathMatcher matcher = new PathMatcher();
  private final Map<String, List<String>> parameters = new HashMap<>();

  @BeforeEach
  void setup() {
    this.parameters.clear();
  }

  private void assertOneParameter(String parameter, String... value) {
    Assertions.assertEquals(1, this.parameters.size(), "these are the parameters: "+String.join(", ", this.parameters.keySet()));
    Assertions.assertTrue(this.parameters.containsKey(parameter), "["+parameter+"] not found in parameter map: "+ String.join(", ", this.parameters.keySet()));
    Assertions.assertEquals(Arrays.asList(value), this.parameters.get(parameter));
  }

  @Test
  void testNoParameters() {
    Assertions.assertTrue(matcher.isMatching("test", "test", this.parameters));
    Assertions.assertTrue(matcher.isMatching("", "", this.parameters));
    Assertions.assertTrue(this.parameters.isEmpty());

    Assertions.assertFalse(matcher.isMatching("test", "fail", this.parameters));
    Assertions.assertFalse(matcher.isMatching("test", "", this.parameters));
    Assertions.assertFalse(matcher.isMatching("", "fail", this.parameters));
    Assertions.assertTrue(this.parameters.isEmpty());
  }

  @Test
  void testWholeParameterNoRegexp() {
    Assertions.assertTrue(matcher.isMatching("{v}", "test", this.parameters));
    this.assertOneParameter("v", "test");
  }

  @Test
  void testPrePostFixedParameterNoRegexp() {
    Assertions.assertTrue(matcher.isMatching("some{var}", "something", this.parameters));
    this.assertOneParameter("var", "thing");

    Assertions.assertTrue(matcher.isMatching("{var}thing", "something", this.parameters));
    this.assertOneParameter("var", "thing", "some");

    Assertions.assertTrue(matcher.isMatching("so{var}ing", "something", this.parameters));
    this.assertOneParameter("var", "thing", "some", "meth");
  }

  @Test
  void testWholeParameterRegexp() {
    Assertions.assertTrue(matcher.isMatching("{num:\\d+}", "42", this.parameters));
    this.assertOneParameter("num", "42");

    Assertions.assertFalse(matcher.isMatching("{num:\\d+}", "42a", this.parameters));
    Assertions.assertFalse(matcher.isMatching("{num:\\d+}", "a42", this.parameters));
  }

  @Test
  void testPrePostFixedParameterRegexp() {
    Assertions.assertTrue(matcher.isMatching("some-{num:[A-F0-9]+}-things", "some-FF-things", this.parameters));
    this.assertOneParameter("num", "FF");
    Stream.of("some-things", "somethings", "some-Ff-things", "nope", "", "some-19a23D-thing")
        .forEach(text -> Assertions.assertFalse(matcher.isMatching("some-{num:[A-F0-9]+}-things", text, this.parameters)));

  }

}