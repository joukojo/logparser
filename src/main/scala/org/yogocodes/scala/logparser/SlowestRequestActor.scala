package org.yogocodes.scala.logparser

import scala.actors.Actor

object SlowestRequestActor extends Actor {

  def act() = {
    loop {
      react {
        case line : String => {
          
        }
      }
      
    }
    
  }
}