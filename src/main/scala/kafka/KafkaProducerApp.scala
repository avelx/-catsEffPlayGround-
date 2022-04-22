package kafka

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO, IOApp, Temporal}
import org.apache.kafka.clients.producer.{KafkaProducer, RecordMetadata}
import cats.implicits._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.DurationInt

object KafkaProducerApp extends IOApp with KafkaProducerHelper {
  override implicit val ex: ExecutionContext = ExecutionContext.global

  def run(args: List[String]): IO[ExitCode] = {
    val data = (250 to 395).map(IO.pure(_)).toList
    val program = for {
      producer <- IO {
        new KafkaProducer[String, String](this.getProducerProps())
      }
      id <- data.sequence
      res <- IO.fromFuture(
        IO {
          sendRecord(id.toString, s"data_$id")(producer)
        }
      )
      _ <- IO {
        println(res.map(_.toString))
      }
    } yield Temporal[IO].sleep(500.milliseconds) >> IO.pure(ExitCode.Success)
    program.unsafeRunSync()
  }
}
