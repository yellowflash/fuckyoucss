package fuckyoucss

import org.jsoup.nodes.Node

trait Selector {
  def matches(node:Node):Boolean
}

case class Stylesheet(rules:List[Rule]) {
  def matching(node:Node) = rules.filter(_.matches(node))
}
case class Rule(selector:List[Selector],declarations:List[Declaration]) {
  def matches(node:Node) = selector.exists(_.matches(node))
}

case class Declaration(name:String,value:String) //Eventually this could also be a trait

case class TagSelector(tag:String) extends Selector{
  def matches(node: Node): Boolean = node.nodeName() == tag
}
case class ClassSelector(clazz:String) extends Selector{
  def matches(node: Node): Boolean = SoupUtils.classes(node) contains clazz
}
case class IdSelector(id:String) extends Selector{
  def matches(node: Node): Boolean = SoupUtils.id(node) contains id
}
case class MultiSelector(current:Selector,rest:Selector) extends Selector{
  def matches(node: Node): Boolean = current.matches(node) && rest.matches(node)
}