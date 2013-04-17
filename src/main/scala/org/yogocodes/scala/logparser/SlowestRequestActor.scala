package org.yogocodes.scala.logparser

import scala.actors.Actor
import scala.collection.mutable.ListBuffer
import org.slf4j.LoggerFactory
import org.slf4j.Logger

object SlowestRequestActor extends Actor {
val logger: Logger = LoggerFactory.getLogger(SlowestRequestActor.getClass())
  val results = ListBuffer[String]()

  def act() = {
    loop {
      react {
        case line: String => {
          val part = line.substring(line.indexOf('['))
          val result = processLine(part)

          this.synchronized({
            results.append(result)
            val sortedList = results.sorted.reverse
            results.clear
            results.appendAll(sortedList.take(100))
          })
          
          results.foreach(row => {logger.debug(row)})
          
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
}