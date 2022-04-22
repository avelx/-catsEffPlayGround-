import kamon.Kamon

object KamonRunner extends App {
  println("Kamon testing ...")

  Kamon.init()

  for {
    x <- 0 to 1000
  } yield {
    println(s"Data: $x")
    Kamon.counter("app.orders.sent").withoutTags().increment()
  }

  println("Exit")
}
