import cats.effect.{IO, IOApp}
import scala.concurrent.duration._
import java.util.concurrent.{Executors, TimeUnit}


object RunnerApp extends IOApp.Simple {

  val scheduler = Executors.newScheduledThreadPool(1)

  val onSchedule = IO.async_[Unit] { cb =>
    scheduler.schedule(new Runnable {
      def run = cb(Right(()))
    }, 500, TimeUnit.MILLISECONDS)
    ()
  }

  //val run = IO.println("Hello, World!")
  val run =
    for {
      ctr <- IO.ref(0)
      _ <- onSchedule
      wait = IO.sleep(3.second)
      poll = wait *> ctr.get

      _ <- poll.flatMap(IO.println(_)).foreverM.start
      _ <- poll.map(_ % 3 == 0).ifM(IO.println("fizz"), IO.unit).foreverM.start
      _ <- poll.map(_ % 5 == 0).ifM(IO.println("buzz"), IO.unit).foreverM.start

      _ <- (wait *> ctr.update(_ + 1)).foreverM.void
    } yield ()
}