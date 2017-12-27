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

  final case class All(b: Boolean) { def getAll: Boolean = b }

  implicit val allMonoid: Monoid[All] = new Monoid[All] {
    def empty: All = All(true)
    def combine(x: All, y: All): All = All(x.getAll && y.getAll)
  }

  final case class Any(b: Boolean) { def getAny: Boolean = b }

  implicit val anyMonoid: Monoid[Any] = new Monoid[Any] {
    def empty: Any = Any(true)
    def combine(x: Any, y: Any): Any = Any(x.getAny || y.getAny)
  }

  final case class Endo[A](f: A => A) { def getEndo: A => A = f }

  implicit def endoMonoid[A]: Monoid[Endo[A]] = new Monoid[Endo[A]] {
    def empty: Endo[A] = Endo[A](identity)
    def combine(x: Endo[A], y: Endo[A]): Endo[A] = Endo(a => x.getEndo(y.getEndo(a)))
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
