package app

import scala.scalajs.js

import app.FB
import app.FBDraw
case class FBParticle (
  var x: Int,
  var y: Int,
  var r: Int,
  var col: String,
  var typee: String,
  val name: String,
  // determines whether particle will
  // travel to the right of left
  // 50% chance of either happening
  val dir: Int,
  // random values so particles do no
  // travel at the same speeds
  var vx: Double,
  var vy: Double,
  var remove: Boolean
):
  def apply(x: Int, y: Int, r: Int, col: String, typee: String): FBParticle = {
    val dir = if (js.Math.random() * 2 > 1) then 1 else -1
    FBParticle(
      x, y, r, col, typee,
      "particle",
      dir,
      (js.Math.floor(Math.random() * 4) * dir),
      (js.Math.floor(Math.random() * 7)),
      false
    )
  }

  def update(): Unit = {
    // update coordinates
    this.x = this.x + this.vx.toInt;
    this.y = this.y - this.vy.toInt;
    
    // increase velocity so particle
    // accelerates off screen
    this.vx = this.vx * 0.99;
    this.vy = this.vy * 0.99;
    
    // adding this negative amount to the
    // y velocity exerts an upward pull on
    // the particle, as if drawn to the
    // surface
    this.vy = this.vy - 0.35;
    
    // offscreen
    if (this.y > FB.HEIGHT) {
      this.remove = true;
    }
  }

  def render(): Unit = {
    if (this.typee == "star") {
      //FBDraw.star(this.x, this.y, this.col);
    } else {
      FBDraw.circle(this.x, this.y, this.r, this.col);
    }
  }
  