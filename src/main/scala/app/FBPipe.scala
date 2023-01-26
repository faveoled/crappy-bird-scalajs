package app

import scala.scalajs.js

import app.FB
import app.FBDraw
import app.FBEntity
case class FBPipe(x: Int, w: Int) extends FBEntity {
  var centerX: Int = x
  var coin = true
  var centerY: Int = randomIntFromInterval(70, 220);
  val h = FB.HEIGHT - 150
  val vx = -1
  
  def update(): Unit = {
  	// update coordinates
    centerX += vx
    if (centerX == (0 - w)) {
      respawn();
    }
  }

  def render() = {
    if (coin) {
      FBDraw.circle(centerX.toDouble + w.toDouble / 2 - 5, centerY - 5, 5, "Gold")
    }
    FBDraw.rect(centerX, 0, w, centerY - 50, "#8ED6FF")
    FBDraw.rect(centerX, centerY + 50, w, h - centerY, "#8ED6FF")
  }

  def respawn() = {
    centerY = randomIntFromInterval(70, 220);
    centerX = 320 - w + 160;
    coin = true;
  }

  def randomIntFromInterval(min: Int, max: Int) = {
    js.Math.floor(js.Math.random() * (max - min + 1) + min).toInt
  }
}
