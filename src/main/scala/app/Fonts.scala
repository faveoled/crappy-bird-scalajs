package app

import org.scalajs.dom.HTMLImageElement
import app.HtmlUtil

object Fonts {
  
  val all = 
    (0 to 9)
      .map(i => HtmlUtil.image(s"font_small_$i.png"))
      .toList

  def getByIdx(index: Int): HTMLImageElement = {
    all(index)
  }
}
