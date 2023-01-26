package app

import app.FB
import app.FBDraw
import app.FBEntity
class FBTree (var x: Int, var y: Int) extends FBEntity {
  val r = 30
  val w = r * 2
  val h = 50
  var vx = -1
  val typee = "Tree"
  
  def update(): Unit = {
    this.x += this.vx
    if (this.x < (0 - this.r * 2)) {
      this.respawn()
    }
  }

  def render(): Unit = {
    FBDraw.circle(x + r, (y + r) - 10, r, "green") // "#050")
    FBDraw.circle(x + (r / 2), (y + r) - 10, r / 3, "rgba(0,0,0,0.08)")
    FBDraw.rect(x + r, y + r, 10, r, "brown")//, "#d20")
  }
  
  def respawn(): Unit = {
    x = FB.WIDTH + r
  }
}