package org.yogocodes.scala.logparser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.actors.Actor

object App {

  val logger: Logger = LoggerFactory.getLogger(App.getClass())

  def main(args: Array[String]) = {
    logger.info("Hi!")
    val fileName = "/access.log"
    val printerActor = PrinterActor
    val slowestRequestActor = SlowestRequestActor
    val countOccurrenceActor = CountOccurrenceActor
    val actors = List[Actor](countOccurrenceActor)

    actors.foreach(actor => actor.start)

    val reader = new LogFileReader(fileName, actors)

    reader.process

  }
}