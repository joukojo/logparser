package org.yogocodes.scala.logparser

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.actors.Actor
import org.yogocodes.scala.logparser.analyzer.NOPActor
import org.yogocodes.scala.logparser.analyzer.SlowestRequestActor
import org.yogocodes.scala.logparser.analyzer.NOPActor
import org.yogocodes.scala.logparser.analyzer.SlowestRequestActor

object App {

  val logger: Logger = LoggerFactory.getLogger(App.getClass())

  def main(args: Array[String]) = {
    logger.info("Hi!")
    val fileName = args(0)
    
    val parentActor = new LogAnalyzerActor()
    
    val nopActor = new NOPActor(parentActor)
    val slowestRequestActor = new SlowestRequestActor(parentActor)
    val reader = new LogFileReader(fileName, parentActor)
    val actors = List[Actor](nopActor, slowestRequestActor, reader)

    actors.foreach(parentActor.addActor(_))
    logger.debug("starting the parentActor: {}", parentActor)
    parentActor.start

    
    logger.debug("started the parentActor")
    
    //FIXME add logic how to wait for ending of the actors and flushing the result to files
    
    while(true) {
      if( parentActor.getActors.isEmpty ) {
       logger.debug("all actors are removed from the queue ")
        exit
      }
      Thread.sleep(500)
    }
    

  }

}