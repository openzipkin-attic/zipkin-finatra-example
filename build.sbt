name := "finatra-example"
organization := "finatra.example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

fork in run := true

// redirect finatra logs to stdout or stderr
javaOptions ++= Seq(
  "-Dlog.service.output=/dev/stderr",
  "-Dlog.access.output=/dev/stderr")

libraryDependencies ++= Seq(
  "com.twitter" % "finatra-http_2.11" % "2.7.0",
  "com.twitter" % "finatra-httpclient_2.11" % "2.7.0",
  // By placing this in the classpath, spans report to localhost:9411
  // you can change via -Dzipkin.http.host=your_host:9411
  "io.zipkin.finagle" % "zipkin-finagle-http_2.11" % "0.3.4",
  "ch.qos.logback" % "logback-classic" % "1.1.9")
