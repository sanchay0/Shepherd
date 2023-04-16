import sbt.*

object Dependencies {
  val slf4j = "org.slf4j" % "slf4j-simple" % "1.7.25"

  val scalatra = "org.scalatra" %% "scalatra" % "2.8.4"

  val jetty = Seq(
    "org.eclipse.jetty" % "jetty-server" % "9.4.50.v20221201",
    "org.eclipse.jetty" % "jetty-servlet" % "9.4.50.v20221201"
  )
}
