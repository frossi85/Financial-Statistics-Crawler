package com.example

import akka.actor.{Actor, ActorLogging, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class CrawlerActor extends Actor with ActorLogging with AutoMarshaller {
  import CrawlerActor._
  import context.dispatcher

  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))


  val statisticsServiceUrl = {
    val config = ConfigFactory.load()
    config.getString("statisticsServiceUrl")
  }

  def receive = {
  	case FetchData =>
	    log.info("Data crawled!!!")

      println(Await.result(getStatistics(), Duration.Inf))

  }

  def getStatistics(): Future[List[StatisticData]] = {
    implicit val serialization = this.serialization
    implicit val formats = this.formats

    val responseFuture: Future[HttpResponse] =
      Http(context.system).singleRequest(HttpRequest(uri = statisticsServiceUrl))

    responseFuture flatMap  { response =>
      Unmarshal(response.entity).to[StatisticsResponse] map { statisticsResponse =>
        statisticsResponse.query.results.quote
      }
    }
  }
}


object CrawlerActor {
  val props = Props[CrawlerActor]
  case object FetchData
}
