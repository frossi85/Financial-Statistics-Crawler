name := """akka-ecample"""

version := "1.0"

scalaVersion := "2.11.6"

val json4sVersion = "3.3.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.4" % Test,

  "com.typesafe.akka" %% "akka-http-core" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",

  "org.json4s" %% "json4s-core" % json4sVersion,
  "org.json4s" %% "json4s-jackson" % json4sVersion,
  "de.heikoseeberger" %% "akka-http-json4s" % "1.4.1",

  // -- kafka --
  "org.apache.kafka" % "kafka-clients" % "0.8.2.2",
  "org.apache.kafka" % "kafka_2.11" % "0.8.2.2",
  "org.apache.kafka" % "kafka_2.11" % "0.8.2.2" classifier("test"),
  "org.apache.kafka" % "kafka-clients" % "0.8.2.2" % Test,

  "org.scalatest" %% "scalatest" % "2.2.6" % "test")


import Keys._
javaOptions in Test += "-Dconfig.file=test/resources/application.conf"



