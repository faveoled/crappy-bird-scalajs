package app

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import scala.collection.mutable.ArrayBuffer
import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.window
import org.scalajs.dom.window.navigator
import org.scalajs.dom.document
import org.scalajs.dom.KeyboardEvent
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLCanvasElement
import org.scalajs.dom.raw.HTMLSelectElement
import org.scalajs.dom.raw.HTMLInputElement
import org.scalajs.dom.raw.HTMLImageElement
import org.scalajs.dom.raw.HTMLAudioElement
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.CanvasGradient
import org.scalajs.dom.MouseEvent
import org.scalajs.dom.TouchEvent
import org.scalajs.dom.UIEvent
import app.FBBird
import app.FBPipe
import app.Gradients
import app.GradientType
import app.game.Game
import app.game.GameOver
import app.game.Play
import app.game.Splash
import app.game.GameState

import app.App
import app.FBDraw
import app.FBEntity
import app.FBInput
case class Offset(
  var top: Int,
  var left: Int
)

case class Score(
  var taps: Int,
  var coins: Int
)

object FB {
  var Input = FBInput(0, 0, false)
  // set up some inital values
  var WIDTH: Int = 320
  var HEIGHT: Int = 480
  var scale: Double = 1
  // the position of the canvas
  // in relation to the screen
  var offset = Offset(0, 0)
  // store all bird, touches, pipes etc
  var bird: FBBird = null
  var entities: ArrayBuffer[FBEntity] = ArrayBuffer()
  val score = Score(0, 0)

  var distance = 0
  var digits: Array[String] = Array()
  // we'll set the rest of these
  // in the init function
  var RATIO: Double = FB.WIDTH.toDouble / FB.HEIGHT.toDouble
  var bg_grad: GradientType = GradientType.Day
  var game: Game = null
  var currentWidth: Int = FB.WIDTH
  var currentHeight: Int = FB.HEIGHT
  var canvas = document.getElementsByTagName("canvas")(0).asInstanceOf[HTMLCanvasElement];

  // it's important to set this
  // otherwise the browser will
  // default to 320x200
  FB.canvas.width = FB.WIDTH;
  FB.canvas.height = FB.HEIGHT;
  
  // the canvas context allows us to 
	// interact with the canvas api
  var ctx = FB.canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D];

  // we need to sniff out android & ios
	// so we can hide the address bar in
	// our resize function
  var ua = navigator.userAgent.toLowerCase();
  var android = if FB.ua.indexOf("android") > -1 then true else false
  var ios = if (FB.ua.indexOf("iphone") > -1 || FB.ua.indexOf("ipad") > -1) then true else false

  def init(): Unit = {
    window.addEventListener("click", (e: MouseEvent) =>
      e.preventDefault();
      FB.Input.set(e);
    )
    window.addEventListener("touchstart", (e: TouchEvent) => {
      e.preventDefault();
      // the event object has an array
      // called touches, we just want
      // the first touch
      FB.Input.set(e.touches(0));
    }, false);
    window.addEventListener("touchmove", e => {
      // we're not interested in this
      // but prevent default behaviour
      // so the screen doesn't scroll
      // or zoom
      e.preventDefault();
    }, false);
    window.addEventListener("touchend", e => {
      // as above
      e.preventDefault();
    }, false)

    // we're ready to resize
    FB.resize();
    
    FB.changeState(GameState.Splash);

    FB.loop();
  }

  def loop(): Unit = {
    App.requestAnimFrame((highResTs) => 
      FB.loop()
      ()
    );
  
    FB.update();
    FB.render();
  }

  def changeState(state: GameState): Unit = {
    val newGame = state match {
      case GameState.GameOver =>
        GameOver()
      case GameState.Play =>
        Play()
      case GameState.Splash =>
        Splash()
    }
    FB.game = newGame
    FB.game.init()
  }

  def resize(): Unit = {
    FB.currentHeight = window.innerHeight.toInt;
    // resize the width in proportion
    // to the new height
    FB.currentWidth = (FB.currentHeight.toDouble * FB.RATIO.toDouble).toInt;

    // this will create some extra space on the
    // page, allowing us to scroll pass
    // the address bar, and thus hide it.
    if (FB.android || FB.ios) {
      document.body.style.height = (window.innerHeight + 50) + "px";
    }

    FB.canvas.style.width = FB.currentWidth + "px";
    FB.canvas.style.height = FB.currentHeight + "px";

    // set the new canvas style width & height
    // note: our canvas is still 320x480 but

    // the amount by which the css resized canvas
    // is different to the actual (480x320) size.
    FB.scale = FB.currentWidth.toDouble / FB.WIDTH.toDouble;
    // position of canvas in relation to
    // the screen
    FB.offset.top = FB.canvas.offsetTop.toInt;
    FB.offset.left = FB.canvas.offsetLeft.toInt;

    // we use a timeout here as some mobile
    // browsers won't scroll if there is not
    // a small delay
    window.setTimeout(() => {
      window.scrollTo(0, 1);
    }, 1);
  }

  def update(): Unit = {
    FB.game.update();
    FB.Input.tapped = false;
  }

  def render(): Unit = {
    FBDraw.rect(0, 0, FB.WIDTH, FB.HEIGHT, Gradients.byType(FB.bg_grad));
    FB.entities.foreach(e => e.render())
    FB.game.render();
  }

  def Collides(bird: FBBird, pipe: FBPipe): Boolean = {
    if (bird.vy >= 370) {
      return true;
    }
    if (pipe.coin && bird.vx > pipe.centerX.toDouble + pipe.w.toDouble / 2 - 5) {
      pipe.coin = false;
      FB.score.coins += 1;
      FB.digits = FB.score.coins.toString().split("");
      App.play_sound(App.soundScore);
    }
    
    val bx1 = bird.vx - bird.width.toDouble / 2;
    val by1 = bird.vy - bird.height.toDouble / 2;
    val bx2 = bird.vx + bird.width.toDouble / 2;
    val by2 = bird.vy + bird.height.toDouble / 2;
    
    val upx1 = pipe.centerX;
    val upy1 = 0;
    val upx2 = pipe.centerX + pipe.w;
    val upy2 = pipe.centerY - 50;
    
    val lpx1 = pipe.centerX
    val lpy1 = pipe.centerY + 50
    val lpx2 = upx2
    val lpy2 = pipe.h
    
    val c1 = !(bx1 > upx2 ||
      bx2 < upx1 ||
      by1 > upy2 ||
      by2 < upy1)
    val c2 = !(bx1 > lpx2 ||
      bx2 < lpx1 ||
      by1 > lpy2 ||
      by2 < lpy1)
    
    c1 || c2  
  }
}
