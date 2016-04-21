package com.example

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}

class CrawlerActor(kafkaProducer: SimpleKafkaProducer) extends Actor with ActorLogging with AutoMarshaller {
  import CrawlerActor._
  import context.dispatcher

  implicit val system: ActorSystem = context.system
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(system))

  val statisticDataFetcher = new StatisticDataFetcher()
  val dataSender: DataSender = new DataSender(kafkaProducer)


  def receive = {
  	case FetchData =>
      statisticDataFetcher.getStatistics() map { statistics =>
        dataSender.send(statistics)
      }
  }
}

object CrawlerActor {
  val kafkaHelpers = new KafkaHelpers()
  val props = Props(classOf[CrawlerActor], new SimpleKafkaProducer(kafkaHelpers.kafkaSocket(), kafkaHelpers.topic()))
  case object FetchData
}


