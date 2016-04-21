package com.example

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import scala.concurrent.Await
import scala.concurrent.duration._

object CrawlerApp extends App {
  val system = ActorSystem("MyActorSystem")
  val crawlerActor = system.actorOf(CrawlerActor.props, "crawlerActor")

  //Use the system's dispatcher as ExecutionContext
  import system.dispatcher

  val config = ConfigFactory.load()

  val fetchInterval: FiniteDuration = Some(
    Duration(config.getString("fetchInterval"))
  ).collect { case d: FiniteDuration => d }.getOrElse(5 seconds)

  system.scheduler.schedule(initialDelay = Duration.Zero, interval = fetchInterval, crawlerActor, CrawlerActor.FetchData)

  Await.result(system.whenTerminated, Duration.Inf)
}



