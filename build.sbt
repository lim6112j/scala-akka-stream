import Dependencies._

ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala-stream",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.18",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.18",
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.18"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
