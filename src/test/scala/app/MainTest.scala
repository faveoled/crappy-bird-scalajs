package app

import utest._

// import scala.scalajs.js

// import org.scalajs.dom
// import org.scalajs.dom.document
// import org.scalajs.dom.ext._
import app.GradientType

object TutorialTest extends TestSuite {

  def tests = Tests {
    test("HelloWorld") {
      assert(GradientType.Day == GradientType.Night.next())
      assert(GradientType.Day == GradientType.Dusk.next())
    }
  }
}
