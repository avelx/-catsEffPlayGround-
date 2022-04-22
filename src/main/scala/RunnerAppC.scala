import cats.data.Kleisli

object RunnerAppC extends App {
  import cats.implicits._

  val parse: Kleisli[Option,String,Int] =
    Kleisli((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)

  val reciprocal: Kleisli[Option,Int,Double] =
    Kleisli((i: Int) => if (i != 0) Some(1.0 / i) else None)

  val parseAndReciprocal: Kleisli[Option,String,Double] =
    reciprocal.compose(parse)

}
