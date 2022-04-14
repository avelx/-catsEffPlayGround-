package kafka

import cats.effect.{ExitCode, IO, IOApp, Temporal}
import cats.effect.unsafe.implicits.global
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}

object KafkaProducerApp extends IOApp with KafkaProducerHelper {

  def run(args: List[String]): IO[ExitCode] = {
    implicit val ex = ExecutionContext.global

    val program = for {
      producer <- IO {
        new KafkaProducer[String, String](this.getProducerProps())
      }
      _ <- IO.fromFuture (
        IO.blocking {
          val futures = for {
            id <- 10 to 50
            future = Future {
              producer.send(new ProducerRecord[String, String](
              "data-topic",
              0,
              s"key_$id",
              s"Record: $id"
            ))
          }
        } yield future
        Future.sequence(futures)
      })
    } yield Temporal[IO].sleep(5.seconds) >> IO.pure(ExitCode.Success)

    program.unsafeRunSync()
  }

}
