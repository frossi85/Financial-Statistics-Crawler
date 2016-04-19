package com.example

case class StatisticsResponse(query: Query)

case class Query(results: Results)

case class Results(quote: List[StatisticData])

case class StatisticData(
                          //time: Timestamp, //"2016-04-14T20:56:05Z" //This field is not in the response
                          symbol: String,
                          Ask: String, //"Ask": "37.25",
                          AverageDailyVolume: String, //Int, //Or long? is sended as String I need to convert it to an Int?
                          AskRealtime: Option[Any], //Ask what type will receive when not null
                          BidRealtime: Option[Any], //Ask what type will receive when not null
                          Bid: String, //Float, // "37.25",
                          BookValue: String, //Float, //"30.78",
                          Change_PercentChange: String, // "-0.14 - -0.38%", //This is an String?
                          Change: String, //Float, //"-0.14",
                          Commission: Option[Any], //null, //Ask what type will receive when not null
                          Currency: String, //"USD",
                          ChangeRealtime: Option[Any], //null, //Ask what type will receive when not null
                          AfterHoursChangeRealtime: Option[Any], //null, //Ask what type will receive when not null
                          DividendShare: Option[Float], //null, //Ask what type will receive when not null
                          LastTradeDate: String, //Date, //"4/14/2016",
                          TradeDate: Option[Any], //null, //Ask what type will receive when not null
                          EarningsShare: String, //Float, //"-4.64",
                          ErrorIndicationreturnedforsymbolchangedinvalid: Option[Any], //null, //Ask what type will receive when not null
                          EPSEstimateCurrentYear: String, //Float, //"0.53",
                          EPSEstimateNextYear: String, //Float, //"0.60",
                          EPSEstimateNextQuarter: String, //Float, //"0.12",
                          DaysLow: String, //Float, //"36.85",
                          DaysHigh: String, //Float, //"37.50",
                          YearLow: String, //Float, //"26.15",
                          TickerTrend: Option[Any], //null, //Ask what type will receive when not null
                          OneyrTargetPrice: String, //Float, //"38.61",
                          Volume: String, //Int, //"16608991",
                          HoldingsValue: Option[Any], //null, //Ask what type will receive when not null
                          HoldingsValueRealtime: Option[Any], //null, //Ask what type will receive when not null
                          YearRange: String, //"26.15 - 46.13",
                          DaysValueChange: Option[Float], //null, //Ask what type will receive when not null
                          DaysValueChangeRealtime: Option[Float], //null, //Ask what type will receive when not null
                          StockExchange: String, //"NMS",
                          DividendYield: Option[Float], //null, //Ask what type will receive when not null
                          PercentChange: String //Float //"-0.38%" //Or needs to be a string
                        )


