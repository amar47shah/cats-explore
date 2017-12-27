package example

import org.scalatest._
import cats.Monoid
import example.Demo._

class DemoSpec extends FlatSpec with Matchers {
  "The Pair object" should "be combinable with Sum and String members" in {
    val combinedPairs   = Demo.combineAll[Pair[Sum, String]](List(Pair(Sum(1), "abc"), Pair(Sum(2), "def")))
    val combinedFirsts  = Demo.combineAll[Sum](List(Sum(1), Sum(2)))
    val combinedSeconds = Demo.combineAll[String](List("abc", "def"))

    combinedPairs shouldEqual Pair(combinedFirsts, combinedSeconds)
  }

  "The Pair object" should "be combinable with Sum and Product members" in {
    val combinedPairs   = Demo.combineAll[Pair[Sum, Product]](List(Pair(Sum(1), Product(3)), Pair(Sum(2), Product(4))))
    val combinedFirsts  = Demo.combineAll[Sum](List(Sum(1), Sum(2)))
    val combinedSeconds = Demo.combineAll[Product](List(Product(3), Product(4)))

    combinedPairs shouldEqual Pair(combinedFirsts, combinedSeconds)
  }

  "The Sum monoid" should "combine by summing integers" in {
    Monoid[Sum].combine(Sum(3), Sum(4)) shouldEqual Sum(7)
  }

  "The Product monoid" should "combine by multiplying integers" in {
    Monoid[Product].combine(Product(3), Product(4)) shouldEqual Product(12)
  }

  "The String monoid" should "combine by concatenating strings" in {
    Monoid[String].combine("abc", "def") shouldEqual "abcdef"
  }
}
