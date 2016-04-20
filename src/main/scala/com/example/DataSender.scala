package com.example


class DataSender(kafkaProducer: SimpleKafkaProducer) {
  var lastStatisticSent: List[StatisticData] = Nil

  def send(data: List[StatisticData]) = {
    val newStatistics = data.filterNot(lastStatisticSent.toSet)

    newStatistics match {
      case head::tail => {
        kafkaProducer.send[List[StatisticData]](newStatistics)
        lastStatisticSent = data
      }
      case Nil =>
    }
  }
}
