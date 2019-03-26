ThisBuild / scalaVersion     := "2.12.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "vocabulary"

val http4sVersion = "0.20.0-M7"
val scalazZioVersion = "1.0-RC1"

lazy val root = (project in file("."))
  .settings(
    name := "http4s-zio",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-zio" % scalazZioVersion,
  "org.scalaz" %% "scalaz-zio-interop-cats" % scalazZioVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

scalacOptions ++= Seq("-Ypartial-unification")