package example

import cats.Monoid

object Demo {
  def combineAll[A](list: List[A])(implicit A: Monoid[A]): A = list.foldRight(A.empty)(A.combine)

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    def empty: String = ""
    def combine(x: String, y: String): String = x ++ y
  }

  final case class Sum(i: Int) { def getSum: Int = i }

  implicit val sumMonoid: Monoid[Sum] = new Monoid[Sum] {
    def empty: Sum = Sum(0)
    def combine(x: Sum, y: Sum): Sum = Sum(x.getSum + y.getSum)
  }

  final case class Product(i: Int) { def getProduct: Int = i }

  implicit val productMonoid: Monoid[Product] = new Monoid[Product] {
    def empty: Product = Product(1)
    def combine(x: Product, y: Product): Product = Product(x.getProduct * y.getProduct)
  }

  final case class Pair[A, B](first: A, second: B)

  object Pair {
    implicit def pairMonoid[A, B](implicit A: Monoid[A], B: Monoid[B]):
      Monoid[Pair[A, B]] = new Monoid[Pair[A, B]] {
        def empty: Pair[A, B] = Pair(A.empty, B.empty)

        def combine(x: Pair[A, B], y: Pair[A, B]): Pair[A, B] =
          Pair(A.combine(x.first, y.first), B.combine(x.second, y.second))
      }
  }
}
