ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "adentis-test"
  )

libraryDependencies ++= Seq(
  "com.github.pjfanning" %% "scala-faker" % "0.5.2",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test
)