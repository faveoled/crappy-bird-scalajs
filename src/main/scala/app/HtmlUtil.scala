package app

import org.scalajs.dom.HTMLImageElement
import org.scalajs.dom.HTMLAudioElement
import scala.scalajs.js

object HtmlUtil {
  def image(): HTMLImageElement = {
    js.Dynamic
      .newInstance(js.Dynamic.global.Image)()
      .asInstanceOf[HTMLImageElement]
  }

  def image(src: String): HTMLImageElement = {
    val img = js.Dynamic
      .newInstance(js.Dynamic.global.Image)()
      .asInstanceOf[HTMLImageElement]
    img.src = src
    img
  }

  def audio(src: String): HTMLAudioElement = {
    js.Dynamic
      .newInstance(js.Dynamic.global.Audio)(src)
      .asInstanceOf[HTMLAudioElement]
  }

  def audio(): HTMLAudioElement = {
    js.Dynamic
      .newInstance(js.Dynamic.global.Audio)()
      .asInstanceOf[HTMLAudioElement]
  }

}
