import sbt.*

object Dependencies {
  val slf4j = "org.slf4j" % "slf4j-simple" % "1.7.25"

  val scalatra = Seq(
    "org.scalatra" %% "scalatra" % "2.8.4",
    "org.scalatra" %% "scalatra-metrics" % "2.7.0",
    "org.scalatra" %% "scalatra-json" % "2.8.4",
    "org.json4s" %% "json4s-jackson" % "4.0.3"
  )

  val jetty = Seq(
    "org.eclipse.jetty" % "jetty-server" % "9.4.50.v20221201",
    "org.eclipse.jetty" % "jetty-servlet" % "9.4.50.v20221201"
  )

  val guava = "com.google.guava" % "guava" % "31.1-jre"
}
