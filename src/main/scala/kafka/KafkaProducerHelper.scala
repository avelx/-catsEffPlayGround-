package kafka

import kafka.message.DefaultCompressionCodec
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

import java.util.Properties
import scala.concurrent.{ExecutionContext, Future}

trait KafkaProducerHelper {
  implicit val ex: ExecutionContext

  def sendRecord(key: String, value: String)
                (producer: KafkaProducer[String, String]): Future[Either[Throwable, RecordMetadata]] = {
    Future {
      producer.send(new ProducerRecord[String, String]("data-stax",
        0, key, value))
        .get()
    }.map(x => Right(x))
      .recover {
        case ex => Left(ex)
      }
  }

  def getProducerProps(): Properties = {
    val properties = new Properties()
    properties.put("bootstrap.servers", "alpha.avel.local:9092,beta.avel.local:9092,beta.avel.local:9092")
    properties.put("group.id", "consumer-tutorial-2")
    properties.put("compression.codec", DefaultCompressionCodec.codec.toString)
    properties.put("producer.type", "sync")
    properties.put("message.send.max.retries", "5")
    properties.put("request.required.acks", "-1")
    properties.put("serializer.class", "kafka.serializer.StringEncoder")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties
  }
}
