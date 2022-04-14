import cats.effect.unsafe.implicits._
import cats.effect.IO

object RunnerApp2 extends App {
//  val program =  {
//    for {
//      _ <- IO.println("Hello")
//      _ <- IO.println("World")
//    } yield ()
//  }
//  val run = IO.println("Hello") >> IO.println("World")
//  run.unsafeRunSync()
//program.unsafeRunSync()

  import scala.concurrent.duration._

  lazy val loop: IO[Unit] = IO.println("Hello, World!") >> loop
  loop
    .timeout(5.seconds)
    .unsafeRunSync()
}
