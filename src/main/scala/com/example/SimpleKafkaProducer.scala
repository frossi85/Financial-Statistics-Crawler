package com.example

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.json4s.{DefaultFormats, jackson}

class SimpleKafkaProducer(kafkaSocket: Socket, topic: String, brokers: Int = 1) {

  private val serializer = "org.apache.kafka.common.serialization.StringSerializer"

  private def configuration = {
    val props = new Properties()
    props.put("bootstrap.servers", kafkaSocket.toString())
    props.put("key.serializer", serializer)
    props.put("value.serializer", serializer)
    props
  }

  def send[T <: AnyRef](message: T) = {
    implicit val serialization = jackson.Serialization
    implicit val formats = DefaultFormats

    val producer = new KafkaProducer[String, String](configuration)
    val jsonMessage = serialization.write[T](message)
    val data = new ProducerRecord[String, String](topic, jsonMessage)

    producer.send(data)
    producer.close()
  }
}