package org.yogocodes.scala.logparser.analyzer

import scala.actors.Actor
import org.yogocodes.scala.logparser.EndOfFile
import org.yogocodes.scala.logparser.TaskComplete
import org.slf4j.LoggerFactory

class NOPActor(parentActor: Actor) extends Actor {

  val logger = LoggerFactory.getLogger(getClass())
  def act() = {
    loop {
      react {
        case line: String => {
          // NOP 
        }
        case EndOfFile => {
          logger.debug("received a EOF")
          parentActor ! new TaskComplete(this)
          logger.debug("received a EOF")
          exit
        }

      }

    }
  }

}