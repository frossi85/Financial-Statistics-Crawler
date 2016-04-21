package com.example

case class StatisticsResponse(query: Query)

case class Query(results: Results)

case class Results(quote: List[StatisticData])

case class StatisticData(
                          //time: Timestamp, //"2016-04-14T20:56:05Z" //This field is not in the response
                          symbol: String,
                          Ask: String,
                          AverageDailyVolume: String,
                          AskRealtime: Option[String],
                          BidRealtime: Option[String],
                          Bid: String,
                          BookValue: String,
                          Change_PercentChange: String,
                          Change: String,
                          Commission: Option[String],
                          Currency: String,
                          ChangeRealtime: Option[String],
                          AfterHoursChangeRealtime: Option[String],
                          DividendShare: Option[Double],
                          LastTradeDate: String,
                          TradeDate: Option[String],
                          EarningsShare: String,
                          ErrorIndicationreturnedforsymbolchangedinvalid: Option[String],
                          EPSEstimateCurrentYear: String,
                          EPSEstimateNextYear: String,
                          EPSEstimateNextQuarter: String,
                          DaysLow: String,
                          DaysHigh: String,
                          YearLow: String,
                          TickerTrend: Option[String],
                          OneyrTargetPrice: String,
                          Volume: String,
                          HoldingsValue: Option[String],
                          HoldingsValueRealtime: Option[String],
                          YearRange: String,
                          DaysValueChange: Option[Double],
                          DaysValueChangeRealtime: Option[Double],
                          StockExchange: String,
                          DividendYield: Option[Double],
                          PercentChange: String
                        )
