package fuckyoucss

import org.jsoup.nodes.Node

case class StyledNode(node:Node,styles:Styles,children:List[StyledNode])
case class Styles(styles:Map[String,String])
