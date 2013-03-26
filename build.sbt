organization := "default-470a7c"

name := "calc"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.1"

seq(cucumberSettings : _*)

seq(cucumberSettingsWithTestPhaseIntegration : _*)

libraryDependencies ++= Seq(
"info.cukes"    % "cucumber-junit"  % "1.1.3" % "test",
"com.typesafe.akka" %% "akka-actor" % "2.1.2",
"net.databinder.dispatch" %% "dispatch-core" % "0.9.5",
"org.slf4j" % "slf4j-nop" % "1.7.4",
"net.databinder" %% "unfiltered" % "0.6.7",
"net.databinder" %% "unfiltered-netty-server" % "0.6.7",
"com.rabbitmq" % "amqp-client" % "3.0.4"
)
