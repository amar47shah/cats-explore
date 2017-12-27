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

  "The String monoid" should "combine by concatenating strings" in {
    Monoid[String].combine("abc", "def") shouldEqual "abcdef"
  }

  "The Sum monoid" should "combine by summing integers" in {
    Monoid[Sum].combine(Sum(3), Sum(4)) shouldEqual Sum(7)
  }

  "The Product monoid" should "combine by multiplying integers" in {
    Monoid[Product].combine(Product(3), Product(4)) shouldEqual Product(12)
  }

  "The Any monoid" should "combine using OR" in {
    Monoid[Any].combine(Any(true ), Any(true )) shouldEqual Any(true )
    Monoid[Any].combine(Any(false), Any(true )) shouldEqual Any(true )
    Monoid[Any].combine(Any(true ), Any(false)) shouldEqual Any(true )
    Monoid[Any].combine(Any(false), Any(false)) shouldEqual Any(false)
  }

  "The All monoid" should "combine using AND" in {
    Monoid[All].combine(All(true ), All(true )) shouldEqual All(true )
    Monoid[All].combine(All(false), All(true )) shouldEqual All(false)
    Monoid[All].combine(All(true ), All(false)) shouldEqual All(false)
    Monoid[All].combine(All(false), All(false)) shouldEqual All(false)
  }

  "The Endo monoid" should "compose" in {
    val composed: Endo[Int] = Monoid[Endo[Int]].combine(Endo(x => x + 1), Endo(y => y * 3))
    composed.getEndo(2) shouldEqual 7
  }
}
