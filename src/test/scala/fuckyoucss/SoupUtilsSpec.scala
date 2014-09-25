package fuckyoucss

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.mockito.Mockito._
import org.jsoup.nodes.Node


class SoupUtilsSpec extends FlatSpec with ShouldMatchers{
  "Classes" should "give the list of classes at a dom node" in {
    val node = mock(classOf[Node])
    when(node.attr("class")).thenReturn("item item-header")

    SoupUtils.classes(node) should be(Set("item","item-header"))
  }

  it should "give empty list in case the node doesnot have class attribute" in {
    val node = mock(classOf[Node])
    when(node.attr("class")).thenReturn(null)

    SoupUtils.classes(node) should be(Set.empty)
  }

  "Id" should "give the id of the dom node" in {
    val node = mock(classOf[Node])
    when(node.attr("id")).thenReturn("item")

    SoupUtils.id(node) should be(Set("item"))
  }

  it should "give empty set in case id attribute is not present in the node" in {
    val node = mock(classOf[Node])
    when(node.attr("id")).thenReturn(null)

    SoupUtils.id(node) should be(Set.empty)
  }
}
