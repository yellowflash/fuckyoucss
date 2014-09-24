package fuckyoucss

import scala.util.parsing.combinator.RegexParsers

object CSSParser extends RegexParsers {
  def styleSheet = rep(rule) ^^ {case rules => Stylesheet(rules)}
  def rule = selectors ~ declarations ^^ {case sels~decs => Rule(sels,decs)}

  def selectors = rep1sep(selector, ",")
  def selector: Parser[Selector] = (id | clazz | tag)~opt(selector) ^^{
    case selector~None => selector
    case selector~Some(rest) => MultiSelector(selector,rest)
  }
  def selectorIdentifier = "[^\\s,{};#\\.]+".r
  def clazz = "." ~ selectorIdentifier ^^ {case _~clazz => ClassSelector(clazz)}
  def id = "#" ~ selectorIdentifier ^^ {case _~id => IdSelector(id)}
  def tag = selectorIdentifier ^^ {case tag => TagSelector(tag)}

  def nonDeclarationSeperators = "[^{};:]+".r
  def declarations = "{"~rep(declaration)~"}"^^{case _~decs~_ => decs}
  def declaration = nonDeclarationSeperators ~ ":" ~ nonDeclarationSeperators ~";"^^{case name~_~value~_ => Declaration(name,value)}

  def parse(css:String) = {
    parseAll(styleSheet,css) match {
      case Success(sheet,_) => sheet
      case e:Error => throw new RuntimeException(e.toString())
    }
  }

}
