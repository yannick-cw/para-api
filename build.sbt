name := "para_api"
version := "0.1-SNAPSHOT"
scalaVersion := "2.11.8"

val http4sVersion = "0.14.10"
val http4sDependencies = Seq(
  "org.http4s" %% "http4s-dsl",
  "org.http4s" %% "http4s-blaze-server",
  "org.http4s" %% "http4s-circe"
).map(_ % http4sVersion)

val circeVersion = "0.5.1"

val circeDependecies = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= http4sDependencies ++ circeDependecies ++ Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.0",
  "org.typelevel" %% "cats" % "0.7.2",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.scalacheck" % "scalacheck_2.11" % "1.13.2",
  "org.reactivemongo" % "reactivemongo_2.11" % "0.12.0",
  "com.typesafe" % "config" % "1.3.1"
)

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)
dockerRepository:=Some("rincewind373")