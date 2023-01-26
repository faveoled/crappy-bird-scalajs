package app.game

import scala.scalajs.js
import scala.collection.mutable.ArrayBuffer
import app.App
import app.FB
import app.FBCloud
import app.FBBottomBar
import app.FBTree
import app.FBDraw
import app.HtmlUtil
import app.GradientType

class Splash extends Game {

  val banner = HtmlUtil.image("splash.png")

  def init() : Unit = {
    App.play_sound(App.soundSwoosh);
    FB.distance = 0
    FB.bg_grad = GradientType.Day
    FB.entities = ArrayBuffer()
    FB.score.taps = 0
    FB.score.coins = 0
    //Add entities
    FB.entities += new FBCloud(30, js.Math.floor(js.Math.random() * FB.HEIGHT / 2));
    FB.entities += new FBCloud(130, js.Math.floor(js.Math.random() * FB.HEIGHT / 2));
    FB.entities += new FBCloud(230, js.Math.floor(js.Math.random() * FB.HEIGHT / 2));
    for (i <- 0 until 2) {
      FB.entities += new FBBottomBar(FB.WIDTH * i, FB.HEIGHT - 100, FB.WIDTH);
    }
    FB.entities += new FBTree(js.Math.floor(js.Math.random() * FB.WIDTH).toInt, FB.HEIGHT - 160);
    FB.entities += new FBTree(js.Math.floor(js.Math.random() * FB.WIDTH + 50).toInt, FB.HEIGHT - 160);
    FB.entities += new FBTree(js.Math.floor(js.Math.random() * FB.WIDTH + 100).toInt, FB.HEIGHT - 160);
  }

  def update(): Unit = {
    FB.entities.foreach(_.update())
    if (FB.Input.tapped) {
      FB.changeState(GameState.Play)
      FB.Input.tapped = false
    }
  }
  
  def render(): Unit = {
    FBDraw.image(this.banner, 66, 100)
  }
}
