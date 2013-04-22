import AssemblyKeys._

name := "logparser"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.2"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.2"

fork := true

javaOptions in run += "-Xmx3G"

javaOptions in run += "-server"

javaOptions in run += "-XX:+UseG1GC"

javaOptions in run += "-XX:MaxGCPauseMillis=50"

javaOptions in run += "-XX:+UnlockExperimentalVMOptions"

javaOptions in run += "-XX:GCPauseIntervalMillis=200"

javaOptions in run += "-verbose:gc"

