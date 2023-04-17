lazy val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.10",
  organization := "org.shepherd"
)

lazy val npmBuildTask = taskKey[Unit]("Execute the npm build command to build the ui")

npmBuildTask := {
  "cd shepherd-client/ && npm run build && npm run export"
}

run := (Compile / run).dependsOn(npmBuildTask).evaluated

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
    libraryDependencies ++= Dependencies.jetty ++ Dependencies.scalatra ++ Seq(
      Dependencies.guava
    )
  )

lazy val root = (project in file("."))
  .aggregate(core, server)
  .settings(
    name := "Shepherd",
    commonSettings,
    update / aggregate := false
  )
