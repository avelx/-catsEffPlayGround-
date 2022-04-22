import cats.Monoid

object Monoid {
  def apply[A : Monoid]: Monoid[A] = implicitly[Monoid[A]]

  def combineAll[A : Monoid](list: List[A]): A =
    list.foldRight(Monoid[A].empty)(Monoid[A].combine)

}



object RunnerApp extends App {
  import Monoid._

  // Monoid example
  println("Data test ..")
  val list = List(1, 2, 3, 4, 5)
  val (left, right) = list.splitAt(2)

  val sumLeft = combineAll(left)
  // sumLeft: Int = 3
  val sumRight = combineAll(right)

  //val xs = combineAll( "This is a string".toList.reverse)
  //println(xs)
  // sumRight: Int = 12

  // Now gather the results
  val result = Monoid[Int].combine(sumLeft, sumRight)

  println(result)
}