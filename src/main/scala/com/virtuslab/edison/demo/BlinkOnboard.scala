package com.virtuslab.edison.demo

import akka.actor.ActorSystem
import com.virtuslab.edison.demo.Blinky.Protocol.Signal
import mraa.Dir.DIR_IN
import mraa.Edge.EDGE_RISING
import mraa.{ Gpio, IsrCallback }

/**
 * Created by rzeznik on 30.04.15.
 */
object BlinkOnboard extends App {
  private def initMraa = {
    System.loadLibrary("mraajava")
    //TODO check results
    mraa.mraa.init()
  }

  private def setupButton(buttonPin: Int)(fn: => Unit) = {
    val callback = new IsrCallback {
      override def run() = fn
    }
    val gpio = new Gpio(buttonPin)
    gpio.dir(DIR_IN)
    gpio.isr(EDGE_RISING, callback, null)
  }

  initMraa

  val actorSystem = ActorSystem()
  val blinky = actorSystem.actorOf(Blinky.props(ledPin = 13))

  setupButton(buttonPin = 4) { blinky ! Signal }

  println("Press button to set off Blinky !!!!")
  actorSystem.awaitTermination()
}
