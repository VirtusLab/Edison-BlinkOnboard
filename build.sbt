import Dependencies._
import Settings._
import sbt.Keys._

val `BlinkOnboard`: Project = (project in file(".")).
  settings(common: _*).
  settings(
    name := "BlinkOnboard",
    version := "0.1",
    libraryDependencies ++= akka
  )
