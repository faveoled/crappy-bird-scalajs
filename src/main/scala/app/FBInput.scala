package app

import org.scalajs.dom.MouseEvent
import org.scalajs.dom.Touch

import app.FB
case class FBInput(
  var x: Double,
  var y: Double,
  var tapped: Boolean
):
  def set(data: MouseEvent): Unit = {
    set(data.pageX, data.pageY)
  }

  def set(data: Touch): Unit = {
    set(data.pageX, data.pageY)
  }

  def set(x: Double, y: Double): Unit = {
    this.x = (x - FB.offset.left) / FB.scale;
    this.y = (y - FB.offset.top) / FB.scale;
    tapped = true;
  }