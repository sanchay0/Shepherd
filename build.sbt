lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.10"
)

lazy val core = (project in file("shepherd-core"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      Dependencies.slf4j
    )
  )

lazy val server = (project in file("shepherd-server"))
  .dependsOn(core)
  .settings(
    commonSettings,
    libraryDependencies ++= Dependencies.jetty ++ Seq(
      Dependencies.scalatra
    )
  )

lazy val root = (project in file("."))
  .aggregate(core, server)
  .settings(
    name := "Shepherd",
    commonSettings,
    update / aggregate := false
  )
