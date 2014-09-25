package fuckyoucss

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.jsoup.nodes.Node

class SelectorSpec extends FlatSpec with ShouldMatchers {
  "Stylesheet" should "find the right rules matching the selectors" in {
    val matchingRule = mock(classOf[Rule])
    val anotherMatchingRule = mock(classOf[Rule])
    val notMatchingRule = mock(classOf[Rule])
    val node = mock(classOf[Node])

    when(matchingRule.matches(node)).thenReturn(true)
    when(anotherMatchingRule.matches(node)).thenReturn(true)
    when(notMatchingRule.matches(node)).thenReturn(false)

    Stylesheet(List(matchingRule,anotherMatchingRule,notMatchingRule)).matching(node) should be(List(matchingRule,anotherMatchingRule))
  }

  "Rule" should "match if any of the selectors match" in {
    val matchingSelector = mock(classOf[Selector])
    val notMatchingSelector = mock(classOf[Selector])
    val node = mock(classOf[Node])

    when(matchingSelector.matches(node)).thenReturn(true)
    when(notMatchingSelector.matches(node)).thenReturn(false)

    Rule(List(matchingSelector),List()).matches(node) should be(true)
    Rule(List(matchingSelector,notMatchingSelector),List()).matches(node) should be(true)
    Rule(List(notMatchingSelector),List()).matches(node) should be(false)
  }

  "ClassSelector" should "only match nodes with any of the classes matching the selector class" in {
    val node = mock(classOf[Node])
    val nodeWithoutClass = mock(classOf[Node])

    when(node.attr("class")).thenReturn("item itemheader")

    ClassSelector("item").matches(node) should be(true)
    ClassSelector("itemdesc").matches(node) should be(false)
    ClassSelector("item").matches(nodeWithoutClass) should be(false)
  }

  "IdSelector" should "only match nodes with id matching the selector id" in {
    val node = mock(classOf[Node])
    val nodeWithoutId = mock(classOf[Node])

    when(node.attr("id")).thenReturn("item")

    IdSelector("item").matches(node) should be(true)
    IdSelector("itemdesc").matches(node) should be(false)
    IdSelector("item").matches(nodeWithoutId) should be(false)
  }

  "TagSelector" should "only match nodes with tag matching the selector tag" in {
    val node = mock(classOf[Node])

    when(node.nodeName()).thenReturn("div")

    TagSelector("div").matches(node) should be(true)
    TagSelector("span").matches(node) should be(false)
  }

  "MultiSelector" should "only match nodes which matches all the selectors" in {
    val matchingSelector = mock(classOf[Selector])
    val notMatchingSelector = mock(classOf[Selector])
    val node = mock(classOf[Node])

    when(matchingSelector.matches(node)).thenReturn(true)
    when(notMatchingSelector.matches(node)).thenReturn(false)

    MultiSelector(matchingSelector,matchingSelector).matches(node) should be(true)
    MultiSelector(matchingSelector,notMatchingSelector).matches(node) should be(false)
    MultiSelector(notMatchingSelector,matchingSelector).matches(node) should be(false)
    MultiSelector(notMatchingSelector,notMatchingSelector).matches(node) should be(false)
  }
}
