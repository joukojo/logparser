package org.yogocodes.scala.logparser

import scala.actors.Actor

object PrinterActor extends Actor {
  
  def act() = {
    loop {
      react {
        case line : String => {
          println("" + line)
        }
        
      }
      
    }
    
  }

}