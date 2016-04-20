package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import scala.concurrent.{ExecutionContext, Future}

class StatisticDataFetcher(implicit ec: ExecutionContext, system: ActorSystem, materializer: ActorMaterializer) extends AutoMarshaller {

  val statisticsServiceUrl = {
    val config = ConfigFactory.load()
    config.getString("statisticsServiceUrl")
  }

  def getStatistics(): Future[List[StatisticData]] = {
    implicit val serialization = this.serialization
    implicit val formats = this.formats

    val responseFuture: Future[HttpResponse] =
      Http(system).singleRequest(HttpRequest(uri = statisticsServiceUrl))

    responseFuture flatMap  { response =>
      Unmarshal(response.entity).to[StatisticsResponse] map { statisticsResponse =>
        statisticsResponse.query.results.quote
      }
    }
  }
}
