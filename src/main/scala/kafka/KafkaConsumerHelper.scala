package kafka

import kafka.message.DefaultCompressionCodec
import org.apache.kafka.common.serialization.StringDeserializer

import java.util.Properties

trait KafkaConsumerHelper {

  def getConsumerProps() : Properties = {
    val properties = new Properties()
    properties.put("bootstrap.servers", "alpha.avel.local:9092,beta.avel.local:9092,gamma.avel.local:9092")
    properties.put("group.id", "consumer-tutorial-2")
    properties.put("compression.codec", DefaultCompressionCodec.codec.toString)
    properties.put("producer.type", "sync")
    properties.put("message.send.max.retries", "5")
    properties.put("request.required.acks", "-1")
    properties.put("serializer.class", "kafka.serializer.StringEncoder")
    properties.put("key.deserializer", classOf[StringDeserializer])
    properties.put("value.deserializer", classOf[StringDeserializer])
    properties.put("enable.auto.commit", "false")
    properties
  }
}
