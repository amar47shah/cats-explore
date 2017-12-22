package example

import org.scalatest._
import example.Demo._

class DemoSpec extends FlatSpec with Matchers {
  "The Pair object" should "be combinable when its members are combinable" in {
    val combinedPairs   = Demo.combineAll[Pair[Int, String]](List(Pair(1, "abc"), Pair(2, "def")))
    val combinedFirsts  = Demo.combineAll[Int](List(1, 2))
    val combinedSeconds = Demo.combineAll[String](List("abc", "def"))

    combinedPairs shouldEqual Pair(combinedFirsts, combinedSeconds)
  }
}
