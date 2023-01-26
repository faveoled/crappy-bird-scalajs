package app

import scala.scalajs.js

import app.FB
import app.FBDraw
import app.FBEntity
class FBCloud(var x: Double, var y: Double) extends FBEntity {
  val r: Double = 30
  val col: String = "rgba(255,255,255,1)"
  val typ: String = "cloud"
  val vx = -0.10
  var remove: Boolean = false
  
  def update(): Unit = {
    x += vx
    if (x < (0 - 115)) {
      respawn()
    }
  }

  def render(): Unit = {
    FBDraw.circle(x + r, (y + r), r, col);
    FBDraw.circle(x + 55, (y + r / 2), r / 0.88, col);
    FBDraw.circle(x + 55, (y + r + 15), r, col);
    FBDraw.circle(x + 85, (y + r), r, col);
  }

  def respawn(): Unit = {
    this.x = js.Math.floor(js.Math.random() * this.r * 2) + FB.WIDTH;
    this.y = js.Math.floor(js.Math.random() * FB.HEIGHT / 2)
  }
}