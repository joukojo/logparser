package org.yogocodes.scala.logparser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.actors.Actor

object App {

  val logger: Logger = LoggerFactory.getLogger(App.getClass())

  def main(args: Array[String]) = {
    logger.info("Hi!")
    val fileName = args(0)
    val printerActor = PrinterActor

    val actors = List[Actor](SlowestRequestActor)

    logger.debug("starting the actors")
    actors.foreach(actor => actor.start)

    val reader = new LogFileReader(fileName, actors)
    logger.debug("starting the log file analyze")
    reader.process

    //FIXME add logic how to wait for ending of the actors and flushing the result to files

  }

}