package pl.unforgiven.load.core.path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class PathFinderTest {

  private final PathFinder finder = new PathFinder();

  private void assertResults(String[] path, Map<String, List<String>> parameters, String... expected) {
    final var result = this.finder.findPath(parameters, path);
    if(expected == null || expected.length == 0)
      Assertions.assertTrue(result.isEmpty());
    else {
      Assertions.assertTrue(result.isPresent());
      Assertions.assertArrayEquals(expected, result.get());
      Assertions.assertNotSame(path, result.get());
    }
  }

  @Test
  void testMatchingNoParameters() {
    final var path = new String[]{"this", "must", "match"};
    assertResults(path, PathUtils.toMap(), path);
  }

  @Test
  void testNoMatchingWhenParametersNotNeededButGiven() {
    assertResults(new String[]{"this", "must", "fail"}, PathUtils.toMap("param", "value"));
  }

  @Test
  void testNoMatchingWhenIncorrectParameterGiven() {
    assertResults(new String[]{"this", "{needs:[a-z]+}", "parameter"}, PathUtils.toMap("param", "value"));
  }

  @Test
  void testNoMatchingWhenRegexpsDoNotMatch() {
    assertResults(new String[]{"this", "{number:\\d+}", "is", "absent"}, PathUtils.toMap("number", "is not"));
  }

  @Test
  void testMatchingForAnyRegexp() {
    assertResults(new String[]{"this", "{param}", "works"},
        PathUtils.toMap("param", "anything goes"),
        "this", "anything goes", "works");
  }

  @Test
  void testMatchingWhenRegexpsMatch() {
    assertResults(new String[]{"this", "{number:\\d+}", "is", "absent"},
        PathUtils.toMap("number", "1234"),
        "this", "1234", "is", "absent");
  }

  @Test
  void testMatchingNamedParameterInTheMiddleOfPathElement() {
    assertResults(new String[]{"path-{number:\\d+}a", "test"},
        PathUtils.toMap("number", "666"),
        "path-666a", "test");
  }

  @Test
  void testMatchingNamedParametersInDifferentLocationsOfPathElement() {
    assertResults(new String[]{"end{text}", "{start:\\d\\d}page", "in-{the:[a-z]+}-middle", "end"},
        PathUtils.toMap("start", "27", "the", "of", "text", "something"),
        "endsomething", "27page", "in-of-middle", "end");
  }

  @Test
  void testRepeatedParameters() {
    assertResults(new String[]{"{number:\\d\\d}*", "text"},
        Map.of("number", Arrays.asList("10", "20", "27")),
        "10", "20", "27", "text");
  }

  @Test
  void testRepeatingAndRegularParameters() {
    assertResults(new String[]{"post-{number:\\d+}", "read", "{title}*"},
        Map.of(
        "number", Collections.singletonList("272727"),
        "title", Arrays.asList("some", "words", "one", "after", "another")
        ),
        "post-272727", "read", "some", "words", "one", "after", "another");
  }

}