package com.example

import akka.actor.{Actor, ActorLogging, Props}

class CrawlerActor extends Actor with ActorLogging {
  import CrawlerActor._

  def receive = {
  	case FetchData =>
	    log.info("Data crawled!!!")
  }
}

object CrawlerActor {
  val props = Props[CrawlerActor]
  case object FetchData
}