import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"
lazy val akkaVersion = "2.6.18"
lazy val root = (project in file("."))
  .settings(
    name := "scala-stream",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
