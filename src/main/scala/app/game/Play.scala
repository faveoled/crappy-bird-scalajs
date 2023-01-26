package app.game

import app.App
import app.Fonts
import app.FB
import app.FBPipe
import app.FBBird
import app.FBEntity
import app.FBDraw
import app.HtmlUtil
import app.GradientType
import scala.collection.mutable.ArrayBuffer

class Play extends Game {

  var distance = 0

  def init(): Unit = {
    FB.entities += new FBPipe(FB.WIDTH * 2, 50);
    FB.entities += new FBPipe(FB.WIDTH * 2 + (FB.WIDTH.toDouble / 2).toInt, 50);
    FB.entities += new FBPipe(FB.WIDTH * 3, 50);
    FB.bird = new FBBird();
    FB.entities += FB.bird;
    FB.digits = Array("0");
  }

  def update(): Unit = {
    FB.distance += 1
    val levelUp = 
      if ((FB.distance % 2048) == 0) then true else false

    if (levelUp) {
      FB.bg_grad = FB.bg_grad.next()
    }

    var checkCollision = false; // we only need to check for a collision
    // if the user tapped on this game tick
    // if the user has tapped the screen
    if (FB.Input.tapped) {
      // keep track of taps; needed to 
      // calculate accuracy
      FB.score.taps += 1;
      // set tapped back to false           
      // in the next cycle
      checkCollision = true;
    }

    val pipeHitIdx = 
      FB.entities
        .indexWhere(e => 
          e.isInstanceOf[FBPipe] &&
          FB.Collides(FB.bird, e.asInstanceOf[FBPipe])
        )

    val takeCount = if pipeHitIdx == -1 then FB.entities.length else pipeHitIdx + 1 
    FB.entities.take(takeCount).foreach(_.update())

    if (pipeHitIdx != -1) {
      App.play_sound(App.soundHit)
      FB.changeState(GameState.GameOver)
    }
  }

  def render(): Unit = {
    val X = (FB.WIDTH.toDouble / 2 - (FB.digits.length * 14).toDouble / 2)
    for i <- 0 until FB.digits.length
    do
      FBDraw.image(Fonts.getByIdx(FB.digits(i).toInt), X + (i * 14), 10)
  }
}
