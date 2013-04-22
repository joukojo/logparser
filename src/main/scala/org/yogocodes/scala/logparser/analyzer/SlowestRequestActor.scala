package org.yogocodes.scala.logparser.analyzer

import scala.actors.Actor
import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.yogocodes.scala.logparser.EndOfFile
import org.yogocodes.scala.logparser.TaskComplete

class SlowestRequestActor(parentActor: Actor) extends Actor {
  val logger: Logger = LoggerFactory.getLogger(getClass())
  val results = ListBuffer[String]()

  def act() = {
    loop {
      react {
        case line: String => {
          val part = line.substring(line.indexOf('['))
          val result = processLine(part)
          this.synchronized({
            results.append(result)
          })

          if (results.size > 900) {
            logger.warn("Clearing the results:{}", results.size)
            clearTheResults
            logger.debug("the results.size: {}", results.size)
          }

        }
        case EndOfFile => {
          clearTheResults
          //    results.foreach(row => { logger.debug(row) })
          logger.debug("Received end of file")
          parentActor ! new TaskComplete(this)
          logger.debug("sent task complete to {}", parentActor);
        }
      }

    }

  }

  def processLine(part: String): String = {
    val s = part.split(" ")
    val url = s(2) + " " + s(3)
    val time = s(7)

    time + " " + url
  }

  def clearTheResults() = {
    logger.warn("Clearing the results:{}", results.size)
    this.synchronized({
      val sortedList = results.sorted.reverse
      results.clear
      results.appendAll(sortedList.take(100))

    })

  }
}