package org.yogocodes.scala.logparser

import scala.actors.Actor
import org.slf4j.LoggerFactory
import scala.collection.mutable.ListBuffer

class LogAnalyzerActor extends Actor {

  val actors = ListBuffer[Actor]()

  def getActors = actors

  val logger = LoggerFactory.getLogger(getClass)
  def act() {

    logger.debug("starting given actors")
    actors.foreach(_.start)
    logger.debug("startedgiven actors")
    loop{
      logger.debug("waiting for the events")
      react({
        case complete: TaskComplete => {

          val completedActor = complete.getActor;
          logger.debug("task completed: {}", completedActor.getClass().getName())
          this.synchronized({
            val inx = actors.indexOf(completedActor)
            if (inx != -1) {
              actors.remove(inx)
            } else {
              logger.warn("Okay someone send us an unknown TaskComplete:" + completedActor.getClass().getName())
            }
          })

          logger.debug("still these actors:{}", actors)

          if (actors.isEmpty) {
            logger.debug("actors are empty, exiting"); 
            exit
          }
          
        }
        case actor: Actor => {
          addActor(actor)
          actor.start
        }
      })

      logger.debug("We go round and round")
    }
    logger.debug("parent actor quit")
  }

  def addActor(actor: Actor) = {
    this.synchronized({
      actors.append(actor)
    })
  }

}