package fuckyoucss

trait Selector {}

case class Stylesheet(rules:List[Rule])
case class Rule(selector:List[Selector],declarations:List[Declaration])

case class Declaration(name:String,value:String) //Eventually this could also be a trait

case class TagSelector(tag:String) extends Selector
case class ClassSelector(clazz:String) extends Selector
case class IdSelector(id:String) extends Selector
case class MultiSelector(current:Selector,rest:Selector) extends Selector