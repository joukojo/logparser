package org.yogocodes.scala.logparser

import scala.actors.Actor
import java.io.File
import scala.io.Source
import java.io.BufferedReader
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class LogFileReader(logFileName: String, actors: List[Actor]) {

  val logger: Logger = LoggerFactory.getLogger(getClass())
  def getActors() = actors.toList

  def process() = {

    val file = new File(logFileName)

    if (file.exists()) {
      val source = Source.fromFile(file);

      val reader = source.bufferedReader

      def readFile(reader: BufferedReader, count: Int): Int = {

        if (count % 1000 == 0) {
          logger.debug("read {} lines", count)
        }
        val line = reader.readLine();

        if (line != null) {
          actors.foreach(actor => {
            actor ! line
          })

          readFile(reader, count + 1)
        } else {
          count
        }

      }
      val totalLines = readFile(reader, 0)
      logger.debug("read all lines. total {} lines", totalLines)
    }

  }
}