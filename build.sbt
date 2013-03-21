organization := "default-470a7c"

name := "calc"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.1"

seq(cucumberSettings : _*)

seq(cucumberSettingsWithTestPhaseIntegration : _*)

libraryDependencies ++= Seq(
"info.cukes"    % "cucumber-junit"  % "1.1.3" % "test",
"com.typesafe.akka" %% "akka-actor" % "2.1.2"
)
