package app

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.window
import org.scalajs.dom.document
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.raw.HTMLAudioElement
import org.scalajs.dom.raw.HTMLElement
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.scalajs.js.annotation.JSExport
import app.HtmlUtil
import app.StringExtensions.substringAfterFirst
import app.StringExtensions.substringBeforeFirst


@JSExportTopLevel("App")
object App {
  val soundJump = HtmlUtil.audio("wing.ogg");
  val soundScore = HtmlUtil.audio("point.ogg");
  val soundHit = HtmlUtil.audio("hit.ogg");
  val soundDie = HtmlUtil.audio("die.ogg");
  val soundSwoosh = HtmlUtil.audio("swooshing.ogg");


  case class AudioChannel (
    var channel: HTMLAudioElement,
    var finished: Double // expected end time for this channel 
  )

  val CHANNEL_MAX = 10; // number of channels
  val audiochannels: Array[AudioChannel] = 
      (0 until CHANNEL_MAX)
      .map(i => AudioChannel(HtmlUtil.audio(), -1))
      .toArray


  def requestAnimFrame(callback: (Double) => Any): Int = {
    val result = window.requestAnimationFrame(callback)
    window.setTimeout(() => callback, 1000 / 60)
    result
  }

  def play_sound(sound: HTMLAudioElement) = {
    val thistime = (new js.Date()).getTime()
    val target = audiochannels
      .find(a => a.finished < thistime)
    if (target.isEmpty) {
      throw Exception("No sound channel to play")
    }
    target.get.finished = thistime + sound.duration * 1000
    target.get.channel.src = sound.src;
    target.get.channel.load();
    target.get.channel.play();
  }


  def getCookie(name: String): Option[String] = {
    val ca = document.cookie.split(";")
    ca
      .find(line => line.substringBeforeFirst("=").trim() == name)
      .map(line => line.substringAfterFirst("=").trim())
  }

  def setCookie(cname: String, cvalue: String, exdays: Int): Unit = {
    val d = new js.Date()
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000))
    val expires = "expires=" + d.toUTCString()
    document.cookie = cname + "=" + cvalue + "; " + expires
  }
}
