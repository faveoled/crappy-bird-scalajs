package app.game

import app.FB
import app.FBDraw
import app.App
import app.HtmlUtil
import org.scalajs.dom.window
import org.scalajs.dom.HTMLImageElement

class GameOver extends Game {
  var banner: HTMLImageElement = null
  var medal: HTMLImageElement = null
  var replay: HTMLImageElement = null
  var highscore: Int = -1

  val bronzeMedal = HtmlUtil.image("medal_bronze.png")
  val silverMedal = HtmlUtil.image("medal_silver.png")
  val goldMedal = HtmlUtil.image("medal_gold.png")
  val platinumMedal = HtmlUtil.image("platinum_bronze.png")

  def getMedal(): HTMLImageElement = {
    var score = FB.score.coins
    if (score <= 10)
      return bronzeMedal
    if (score >= 20) 
      return silverMedal
    if (score >= 30) 
      return goldMedal
    if (score >= 40) 
      return platinumMedal
    throw new Exception("Bug found")
  }

  def getHighScore() : Int = {
    val savedScore = App.getCookie("highscore")
    if(savedScore.isDefined) {
      val hs = savedScore.get.toInt
      val currScore = FB.score.coins
      if (currScore > hs) {
        App.setCookie("highscore", currScore.toString, 999)
      }
      return Math.max(hs, currScore)
    } else {
      App.setCookie("highscore", FB.score.coins.toString, 999)
      return FB.score.coins
    }
  }

  def init(): Unit = {
    window.setTimeout(() => {
      App.play_sound(App.soundDie)
      banner = HtmlUtil.image("scoreboard.png")
      medal = getMedal()
      replay = HtmlUtil.image("replay.png")
      highscore = getHighScore()
    }, 500)
  }

  def update(): Unit = {
    if (FB.Input.tapped) {
      val x = FB.Input.x
      val y = FB.Input.y
      if ( (x >= 102.5 && x <= 102.5 + 115) && (y >= 260 && y <= 260 + 70) ) {
        FB.changeState(GameState.Splash)
      }
      FB.Input.tapped = false
    }
    FB.bird.update()
  }

  def render(): Unit = {
    if (banner != null) {
      FBDraw.image(banner, 42, 70);
      FBDraw.image(medal, 75, 183);
      FBDraw.image(replay, 102.5, 260);
      FBDraw.text(FB.score.coins.toString, 220, 185, 15, "black");
      FBDraw.text(highscore.toString, 220, 225, 15, "black");
    }
  }
}
