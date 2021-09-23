import sbt._

object Versions {

  final val akka         = "2.6.16"
  final val mockitoScala = "1.16.42"
  final val scalatest    = "3.2.10"

}

object Dependencies {

  final val akkaActor    = "com.typesafe.akka" %% "akka-actor"    % Versions.akka
  final val akkaTestkit  = "com.typesafe.akka" %% "akka-testkit"  % Versions.akka
  final val mockitoScala = "org.mockito"       %% "mockito-scala" % Versions.mockitoScala
  final val scalatest    = "org.scalatest"     %% "scalatest"     % Versions.scalatest

}
