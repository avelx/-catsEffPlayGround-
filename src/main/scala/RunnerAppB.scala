import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try

object RunnerAppB extends App {

  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not a number"))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): EitherT[Future, String, Double] =
    for {
      a <- EitherT(parseDoubleAsync(inputA))
      b <- EitherT(parseDoubleAsync(inputB))
      result <- EitherT(divideAsync(a, b))
    } yield result

  val res = divisionProgramAsync("4", "2").value
  println(res)

  val number: EitherT[Option, String, Int] = EitherT.rightT(5)
  val error: EitherT[Option, String, Int] = EitherT.leftT("Not a number")


}
