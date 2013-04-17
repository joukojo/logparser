package org.yogocodes.scala.logparser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.actors.Actor

object App {

  val logger: Logger = LoggerFactory.getLogger(App.getClass())

  def main(args: Array[String]) = {
    logger.info("Hi!")
    val fileName = "/home/joukojo/workspace-scala//logparser/localhost_access_log.2013-04-16.log"
    val printerActor = PrinterActor
    val slowestRequestActor = SlowestRequestActor 
    val actors = List[Actor](printerActor)

    actors.foreach(actor => actor.start)

    val reader = new LogFileReader(fileName, actors)

    reader.process

  }

}