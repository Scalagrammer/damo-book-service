import sbt._

name := "sample-service"

version := "0.1"

scalaVersion := "2.13.3"

libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0"
libraryDependencies += "com.github.finagle" %% "finchx-core"  % "0.32.1"
libraryDependencies += "com.github.finagle" %% "finchx-circe" % "0.32.1"
libraryDependencies += "com.softwaremill.macwire" %% "macros" % "2.3.7" % "provided"
libraryDependencies += "com.typesafe" % "config" % "1.3.3"

libraryDependencies += "org.tpolecat" %% "doobie-postgres" % "0.8.8"
libraryDependencies += "org.tpolecat" %% "doobie-postgres-circe" % "0.8.8"
libraryDependencies += "org.tpolecat" %% "doobie-hikari" % "0.8.8"
libraryDependencies += "com.iheart" %% "ficus" % "1.5.0"

libraryDependencies += "io.circe" %% "circe-core"             % "0.13.0"
libraryDependencies += "io.circe" %% "circe-generic"          % "0.13.0"
libraryDependencies += "io.circe" %% "circe-jawn"             % "0.13.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "org.scalamock" %% "scalamock" % "5.0.0" % Test
