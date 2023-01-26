package app

import scala.scalajs.js

import app.FB
import app.FBDraw
import app.FBEntity
class FBBottomBar (var x: Int, var y: Int, var r: Int) extends FBEntity {
  val vx = -1;
  val name = "BottomBar";

  def update(): Unit= {
    // update coordinates
    this.x += this.vx;
    if (this.x < (0 - this.r)) {
      this.respawn();
    }
  }

  def render(): Unit= {
    FBDraw.rect(this.x, this.y, this.r, 100, "#D2691E");
    for (i <- 0 until 10) {
      FBDraw.semiCircle(this.x + i * (this.r / 9), this.y, 20, "#050");
    }
  }

  def respawn(): Unit= {
    this.x = FB.WIDTH - 1;
  }
}