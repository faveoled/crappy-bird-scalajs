package app

import org.scalajs.dom
import org.scalajs.dom.document

import org.scalajs.dom.window

import app.FB
object Main {
  def main(args: Array[String]): Unit = {
    window.addEventListener("load", (e) => FB.init(), false);
    window.addEventListener("resize", (e) => FB.resize(), false);
  }
}
