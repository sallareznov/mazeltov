import Dependencies._
import DependencyScopes._

name := "mazeltov"

version := "0.1"

scalaVersion := "2.13.6"

libraryDependencies ++=
  compileDependencies(
    akkaActor
  ) ++
    testDependencies(
      akkaTestkit,
      mockitoScala,
      scalatest
    )
