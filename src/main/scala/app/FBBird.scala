package app

import org.scalajs.dom.HTMLImageElement
import scala.scalajs.js

import app.App
import app.FB
import app.FBDraw
import app.FBEntity
import app.HtmlUtil
class FBBird extends FBEntity {

  var img = HtmlUtil.image("bird.png");
  var gravity: Double = 0.25
  var width = 34
  var height = 24
  var ix: Double = 0
  var iy: Double = 0
  var fr: Double = 0
  var vy: Double = 180
  var vx: Double = 70
  var velocity: Double = 0.0
  var play = false
  var jump: Double = -4.6
  var rotation: Double = 0
  var type_ = "bird"



  def update(): Unit = {
    this.fr += 1
    if (this.fr > 5) {
      this.fr = 0;
      if (this.iy == this.height * 3) {
        this.iy = 0
      }
      this.iy += this.height;
    }
    if (this.play) {
      this.velocity += this.gravity;
      this.vy += this.velocity;
      if (this.vy <= 0) {
        this.vy = 0;
      }
      if (this.vy >= 370) {
        this.vy = 370;
      }
      this.rotation = js.Math.min((this.velocity / 10) * 90, 90);
    }
    if (FB.Input.tapped) {
      this.play = true;
      App.play_sound(App.soundJump);
      this.velocity = this.jump;
    }
  }

  def render(): Unit = {
    FBDraw.sprite(this.img, this.ix, this.iy, this.width, this.height, this.vx, this.vy, this.width, this.height, this.rotation);
  }
}
