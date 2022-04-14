package kafka

import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.concurrent.ExecutionContext
import scala.jdk.CollectionConverters.{IterableHasAsJava, IterableHasAsScala}

object KafkaConsumerApp extends KafkaConsumerHelper {

  def main(args: Array[String]): Unit = {
    implicit val ex = ExecutionContext.global

    val kafkaConsumer = new KafkaConsumer[String, String](this.getConsumerProps())
    kafkaConsumer.subscribe(Iterable[String]("data-topic").asJavaCollection)

    while (true) {
      val results = kafkaConsumer.poll(2000).asScala
      if (results.toList.length > 0) {
        println(s"Count: ${results.toList.length}")
        results.foreach(rec => {
          println(s"Record: ${rec.key()}")
        })
        kafkaConsumer.commitSync()

      }
    }

  }

}
