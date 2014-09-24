package fuckyoucss

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class CSSParserSpec extends FlatSpec with ShouldMatchers {
  "CSSParser" should "parse a single rule with id selector and margin declaration" in {
    val sheet: Stylesheet = CSSParser.parse("#item { margin: auto;}")

    sheet.rules should contain(Rule(List(IdSelector("item")),List(Declaration("margin","auto"))))
    sheet.rules should have(size(1))
  }

  it should "parse multiple rules" in {
    val sheet: Stylesheet = CSSParser.parse(
      """h1{ margin: auto;}
         #answer { display: none; }""")
    sheet.rules should contain(Rule(List(TagSelector("h1")),List(Declaration("margin","auto"))))
    sheet.rules should contain(Rule(List(IdSelector("answer")),List(Declaration("display","none"))))
    sheet.rules should have(size(2))
  }

  it should "parse multiple selectors" in {
    val sheet: Stylesheet = CSSParser.parse("h2, h1, #items, .item { margin: auto; color: #cc0000; }")
    sheet.rules should contain(Rule(List(TagSelector("h2"),TagSelector("h1"),IdSelector("items"),ClassSelector("item")),List(Declaration("margin","auto"),Declaration("color","#cc0000"))))
  }

  it should "parse multi selectors as in div.note" in {
    val sheet: Stylesheet = CSSParser.parse("div.note { margin-bottom: 20px;}")
    sheet.rules should contain(Rule(List(MultiSelector(TagSelector("div"),ClassSelector("note"))),List(Declaration("margin-bottom","20px"))))
  }

}
