name := "6.824"

version := "0.1"

scalaVersion := "2.12.2"

sbtVersion := "0.13"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.6"
libraryDependencies +=  "com.typesafe.akka" %% "akka-testkit" % "2.5.6" % Test
