package com.example

import java.nio.charset.StandardCharsets

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.testkit.{ImplicitSender, TestKit}
import kafka.consumer.ConsumerConfig
import kafka.utils.TestUtils
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import org.scalatest.concurrent.ScalaFutures
import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.typesafe.config.ConfigFactory
import scala.concurrent.Future
import scala.collection.immutable.HashMap
import org.json4s.{DefaultFormats, jackson}

class CrawlerActorSpec(_system: ActorSystem) extends TestKit(_system)
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with ScalaFutures
  with BeforeAndAfterAll {

  private val topic = "financial_statistics"
  private val groupId = "group0"
 
  def this() = this(ActorSystem("MySpec"))
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "The Crawler actor" must {
    "send financial statistics to kafka" in {
      //Initialize a Kafka Test Server
      val server = new KafkaTestServer
      server.cleanPreviousData(groupId)
      server.createTopic(topic)

      //Send a message to the crawler actor to crawl some data
      val kafkaProducer = new SimpleKafkaProducer(server.host, server.port, topic)
      val crawlerActor = system.actorOf(Props(classOf[CrawlerActor], kafkaProducer), "crawlerActor")
      crawlerActor ! CrawlerActor.FetchData


      //Create consumer
      val consumerProperties = TestUtils.createConsumerProperties(server.zkConnect, groupId, "consumer0", -1)
      val consumer = kafka.consumer.Consumer.create(new ConsumerConfig(consumerProperties))

      val topicCountMap = HashMap(topic -> 1)
      val consumerMap = consumer.createMessageStreams(topicCountMap)

      val stream = consumerMap.get(topic).get(0)
      val iterator = stream.iterator()

      //The consumer must have at least one message
      iterator.hasNext() shouldEqual true

      val message = new String(iterator.next().message(), StandardCharsets.UTF_8)


      implicit val serialization = jackson.Serialization
      implicit val formats = DefaultFormats

      val unmarshaledMessage = serialization.read[List[StatisticData]](message)

      //Should have at 5 items, one for each of: YAHOO, APPLE, GOOGLE, MICROSOFT and INTUIT
      unmarshaledMessage.length shouldEqual 5

      // cleanup
      consumer.shutdown()
      server.shutdown()
    }
  }
}
