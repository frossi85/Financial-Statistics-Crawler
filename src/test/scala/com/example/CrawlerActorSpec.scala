package com.example

import java.nio.charset.StandardCharsets
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.server
import akka.testkit.{ImplicitSender, TestKit}
import kafka.consumer.ConsumerConfig
import kafka.utils.TestUtils
import org.json4s.{DefaultFormats, jackson}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.collection.immutable.HashMap

class CrawlerActorSpec(_system: ActorSystem) extends TestKit(_system)
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with ScalaFutures
  with BeforeAndAfterAll {

  val kafkaHelpers = new KafkaHelpers()
  private val topic = kafkaHelpers.topic()
  private val groupId = "group0"
 
  def this() = this(ActorSystem("MySpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "The Crawler actor" must {
    "send financial statistics to kafka" in {


      //Send a message to the crawler actor to crawl some data
      val kafkaProducer = new SimpleKafkaProducer(kafkaHelpers.kafkaSocket(), topic)
      val crawlerActor = system.actorOf(Props(classOf[CrawlerActor], kafkaProducer), "crawlerActor")
      crawlerActor ! CrawlerActor.FetchData


      //Create consumer
      val consumerProperties = TestUtils.createConsumerProperties(kafkaHelpers.zookeeperSocket().toString(), groupId, "consumer0", -1)
      val consumer = kafka.consumer.Consumer.create(new ConsumerConfig(consumerProperties))

      val topicCountMap = HashMap(topic -> 1)
      val consumerMap = consumer.createMessageStreams(topicCountMap)

      val stream = consumerMap.get(topic).get(0)
      val iterator = stream.iterator()

      //The consumer must have at least one message
      iterator.hasNext() shouldEqual true


      (1 to 5).foreach(x => {
        implicit val serialization = jackson.Serialization
        implicit val formats = DefaultFormats

        val message = new String(iterator.next().message(), StandardCharsets.UTF_8)
        val symbol = serialization.read[StatisticData](message).symbol

        List("YHOO", "AAPL", "GOOG", "MSFT", "INTU") should contain (symbol)
      })

      // cleanup
      consumer.shutdown()
    }
  }
}
