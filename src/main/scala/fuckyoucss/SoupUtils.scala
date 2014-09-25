package fuckyoucss

import org.jsoup.nodes.Node

object SoupUtils {
  def classes(node:Node) = {
    val clazz: String = node.attr("class")
    if(clazz == null) Set[String]() else clazz.split(" ").filter(!_.isEmpty).toSet
  }

  def id(node:Node) = {
    val id: String = node.attr("id")
    if(id == null) Set[String]() else id.split(" ").filter(!_.isEmpty).toSet
  }
}
