package org.yogocodes.scala.logparser

import scala.actors.Actor
import java.io.File
import scala.io.Source
import java.io.BufferedReader
import org.slf4j.LoggerFactory
import org.slf4j.Logger

class LogFileReader(logFileName: String, parentActor: LogAnalyzerActor) extends Actor {

  val logger: Logger = LoggerFactory.getLogger(getClass())

  def act() = process()

  def process() = {

    val file = new File(logFileName)

    if (file.exists()) {
      val source = Source.fromFile(file);

      var count = 0;
      source.getLines.toIterator.foreach(line => {
        count = count + 1;
        if (count % 1000 == 0) {
          logger.debug("read {} lines", count)
        }

        parentActor.getActors.foreach(actor => {
          if (actor != this) {
            actor ! line
          }
        })

      })

      logger.info("Log file read")
      parentActor.getActors.foreach(actor => {
        if (actor != this) {
          logger.debug("calling EOF for actor: {}", actor)
          actor ! EndOfFile
        }
      })

      logger.info("All parentActor controller actors notified about EndOfFile")
      parentActor ! new TaskComplete(this)
      logger.info("notified the parent for process end")

      //      val reader = source.bufferedReader
      //
      //      def readReader(reader: BufferedReader, count: Int): (String, Int) = {
      //        val line = reader.readLine()
      //
      //        if (line != null) {
      //          actors.foreach(actor => {
      //            actor ! line
      //          })
      //        } else {
      //          actors.foreach(actor => {
      //            actor ! EndOfFile
      //          })
      //        }
      //
      //        (line, count + 1)
      //      }
      //
      //      def readFile(reader: BufferedReader, count: Int): Int = {
      //
      //        if (count % 1000 == 0) {
      //          logger.debug("read {} lines", count)
      //        }
      //        val line = reader.readLine();
      //
      //        if (line != null) {
      //          actors.foreach(actor => {
      //            actor ! line
      //          })
      //
      //          readFile(reader, count + 1)
      //        } else {
      //          actors.foreach(actor => {
      //            actor ! EndOfFile
      //          })
      //          count
      //        }
      //
      //      }
      //
      //      while (readReader(reader, 0)._1 != null) {
      //
      //      }
      //
    }

  }
}