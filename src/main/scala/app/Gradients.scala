package app

import org.scalajs.dom.CanvasGradient

import app.FB
import app.GradientType
object Gradients {

  val all = getGradients()

  def getGradients(): List[CanvasGradient] = {
    // setup some gradients
    val dawn = FB.ctx.createLinearGradient(0, 0, 0, FB.HEIGHT);
    dawn.addColorStop(0, "#036");
    dawn.addColorStop(0.5, "#69a");
    dawn.addColorStop(1, "yellow");

    val day = FB.ctx.createLinearGradient(0, 0, 0, FB.HEIGHT);
    day.addColorStop(0, "#69a");
    day.addColorStop(0.5, "#9cd");
    day.addColorStop(1, "#fff");

    val dusk = FB.ctx.createLinearGradient(0, 0, 0, FB.HEIGHT);
    dusk.addColorStop(0, "#036");
    dusk.addColorStop(0.3, "#69a");
    dusk.addColorStop(1, "pink");

    val night = FB.ctx.createLinearGradient(0, 0, 0, FB.HEIGHT);
    night.addColorStop(0, "#036");
    night.addColorStop(1, "black");
    List(dawn, day, dusk, night)
  }

  def byType(gType: GradientType): CanvasGradient = {
    gType match {
      case GradientType.Dawn => all(0)
      case GradientType.Day => all(1)
      case GradientType.Dusk => all(2)
      case GradientType.Night => all(3)
    }
  }
}
