package app

import org.scalajs.dom.HTMLImageElement
import scala.scalajs.js
import org.scalajs.dom.CanvasGradient

import app.FB
object FBDraw {
  def clear(): Unit = {
    FB.ctx.clearRect(0, 0, FB.WIDTH, FB.HEIGHT)
  }

  def rect(x: Double, y: Double, w: Double, h: Double, col: CanvasGradient): Unit = {
    FB.ctx.fillStyle = col
    FB.ctx.fillRect(x, y, w, h)
  }

  def rect(x: Double, y: Double, w: Double, h: Double, col: String): Unit = {
    FB.ctx.fillStyle = col
    FB.ctx.fillRect(x, y, w, h)
  }

  def circle(x: Double, y: Double, r: Double, col: String): Unit = {
    FB.ctx.fillStyle = col
    FB.ctx.beginPath()
    FB.ctx.arc(x + 5, y + 5, r, 0, js.Math.PI * 2, true)
    FB.ctx.closePath()
    FB.ctx.fill()
  }

  def image(img: HTMLImageElement, x: Double, y: Double): Unit = {
    FB.ctx.drawImage(img, x, y)
  }

  def sprite(img: HTMLImageElement, srcX: Double, srcY: Double, srcW: Int, srcH: Int, 
    destX: Double, destY: Double, destW: Int, destH: Int, r: Double): Unit = {
    FB.ctx.save()
    FB.ctx.translate(destX, destY)
    FB.ctx.rotate(r * (Math.PI / 180))
    FB.ctx.translate(-(destX + destW / 2), -(destY + destH / 2))
    FB.ctx.drawImage(img, srcX, srcY, srcW, srcH, destX, destY, destW, destH)
    FB.ctx.restore()
  }

  def semiCircle(x: Int, y: Int, r: Int, col: String): Unit = {
    FB.ctx.fillStyle = col
    FB.ctx.beginPath()
    FB.ctx.arc(x, y, r, 0, Math.PI, false)
    FB.ctx.closePath()
    FB.ctx.fill()
  }

  def text(string: String, x: Int, y: Int, size: Int, col: String) = {
    FB.ctx.font = "bold " + size + "px Monospace"
    FB.ctx.fillStyle = col
    FB.ctx.fillText(string, x, y)
  }
}
