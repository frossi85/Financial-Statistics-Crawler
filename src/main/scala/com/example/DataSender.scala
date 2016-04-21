package com.example

class DataSender(kafkaProducer: SimpleKafkaProducer) {
  var lastStatisticSent: List[StatisticData] = Nil

  def send(data: List[StatisticData]) = {
    val updatedStatistics = data.filterNot(x =>
      lastStatisticSent
        .map(y => (y.symbol, y.Ask, y.Bid))
        .contains((x.symbol, x.Ask, x.Bid))
    )

    updatedStatistics.map(x => kafkaProducer.send[StatisticData](x))

    if(!updatedStatistics.isEmpty) lastStatisticSent = data
  }
}
