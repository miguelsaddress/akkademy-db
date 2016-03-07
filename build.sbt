name := """akkademy-db-scala"""
version := "0.0.1-SNAPSHOT"
organization := "com.akkademy-db"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.3.11",
    "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
    "com.typesafe.akka" %% "akka-remote" % "2.3.6",
    "org.scalatest" %% "scalatest" % "2.1.7" % "test"
)

lazy val root = Project("root", file(".")) dependsOn(akkademyMessages)
lazy val akkademyMessages = ProjectRef(uri("https://github.com/miguelsaddress/akkademy-db-messages.git"), "akkademy-db-messages")
