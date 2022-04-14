package kafka

import kafka.message.DefaultCompressionCodec

import java.util.Properties

trait KafkaProducerHelper {

  def getProducerProps(): Properties = {
    val properties = new Properties()
    properties.put("bootstrap.servers", "localhost:9092")
    properties.put("group.id", "consumer-tutorial")
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
