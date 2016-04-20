package com.example
import java.nio.charset.StandardCharsets

import kafka.consumer.ConsumerConfig
import kafka.utils.TestUtils
import org.scalatest.{FunSpec, Matchers}
//import org.junit.Assert._
import scala.collection.immutable.HashMap

/*
class SimpleKafkaProducerTest extends FunSpec with Matchers{

  private val topic = "test"
  private val groupId = "group0"

  case class MessageData(a: String, b: String)

  describe("The SimpleKafka Api") {
    it("Should send data using a producer") {
      println("Test started!!!")

      val server = new KafkaTestServer
      server.cleanPreviousData(groupId)
      server.createTopic(topic)

      //Send data to Kafka
      val kafkaApi = new SimpleKafkaProducer("127.0.0.1", server.port, topic)
      kafkaApi.send[MessageData](new MessageData("Hello", "World"))

      //Create consumer
      val consumerProperties = TestUtils.createConsumerProperties(server.zkConnect, groupId, "consumer0", -1)
      val consumer = kafka.consumer.Consumer.create(new ConsumerConfig(consumerProperties))

      val topicCountMap = HashMap(topic -> 1)
      val consumerMap = consumer.createMessageStreams(topicCountMap)
      val stream = consumerMap.get(topic).get(0)
      val iterator = stream.iterator()
      val msg = new String(iterator.next().message(), StandardCharsets.UTF_8)

      assert("{\"a\":\"Hello\",\"b\":\"World\"}" == msg)

      // cleanup
      consumer.shutdown()
      server.shutdown()
    }
  }
}
*/
