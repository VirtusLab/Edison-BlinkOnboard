import sbt._

object Dependencies {
  val akka = {
    val version = "2.3.10"
    Seq("com.typesafe.akka" %% "akka-actor" % version)
  }
}