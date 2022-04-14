import cats.data.EitherT
import cats.effect.{IO}

import scala.util.Try

object RunnerApp3 extends App {
  import cats.effect.unsafe.implicits.global

  def abc(x: Int, y : Int): IO[Either[Throwable, Double]]
    = IO { Try( x.toDouble / y.toDouble).toEither }

  def mod2(x: Int) : IO[Either[Throwable, Boolean]]
    = IO { Try( x % 2 == 0).toEither }

  def printLn(x: Any) : IO[Unit]
    = IO { println(x) }

//  val res  = for {
//    x <- mod2(17)
//    y <- EitherT.right(x)
//    //_ <- EitherT.right(y)
//    //_ <- printLn(y)
//  } yield x
//
//  res.unsafeRunSync()

  val numberO: Option[Int] = Some(5)
  val errorO: Option[String] = Some("Not a number")

  val number: EitherT[Option, String, Int] = EitherT.right(numberO)
  val error: EitherT[Option, String, Int] = EitherT.left(errorO)

  val res = for {
    x <- printLn(error)
  } yield x

  res.unsafeRunSync()
}