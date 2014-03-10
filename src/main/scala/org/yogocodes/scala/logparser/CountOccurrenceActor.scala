package org.yogocodes.scala.logparser

import scala.actors.Actor
import scala.collection.mutable.{Map}
import collection.immutable.ListMap

object CountOccurrenceActor extends Actor {

val checkFor = Map (
  "search for this string" -> 0,
  "and count the occurrences" -> 0,
  "also this gets searched" -> 0,
  "and this" -> 0,
  "this too" -> 0
)

  def act() = {
    loop {
      react {
        case line : String => {
          checkFor.keys.foreach{i => 
            //println("Checking for: " + i)
            if (line.contains(i)) {
              //println("Found! " + i)
              checkFor(i) = checkFor(i) + 1
            }
          }
        }
        case _ => {
          //println("Received stop!")
          ListMap(checkFor.toList.sortBy{_._2}:_*).keys.foreach{i => 
            println(i + " : " + checkFor(i))
          }
          exit()
        }
      }
    }
  }
}