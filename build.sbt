
name := "scr-2024-02-play"

organization := "ru.otus"

version := "0.1"

scalaVersion := "2.11.5"

libraryDependencies += "com.google.inject" % "guice" % "4.2.3"

val sharedSettings = List(
  version := "0.1",
  scalaVersion := "2.11.5"
)

lazy val di = Project(id = "di", base = file("modules/di"))
  .settings(sharedSettings :_*)
  .settings(libraryDependencies += "net.codingwell" %% "scala-guice" %  "4.0.0")

lazy val scr = Project(id = "scr", base = file("modules/scr"))
  .dependsOn(di)
  .settings(sharedSettings :_*)
  .enablePlugins(PlayScala)


lazy val root = (project in file("."))
  .settings(sharedSettings :_*)
  .dependsOn(scr)
  .aggregate(di)
  .enablePlugins(PlayScala)