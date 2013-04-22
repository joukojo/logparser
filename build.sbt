import AssemblyKeys._

name := "logparser"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.2"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.2"

fork := true

javaOptions in run += "-Xmx6G"

