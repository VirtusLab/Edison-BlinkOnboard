package com.virtuslab.edison.demo

import akka.actor.{ Actor, Props }
import mraa.Dir.DIR_OUT
import mraa.Gpio

/**
 * Created by rzeznik on 30.04.15.
 */
class Led(ledPin: Int) extends Actor {
  import Led.Protocol._

  private val gpio = new Gpio(ledPin)
  gpio.dir(DIR_OUT)

  def ledOff: Receive = {
    case Clock => turnOn
    case Off    =>
  }

  def ledOn: Receive = {
    case Clock => turnOff
    case Off    => turnOff
  }

  override def receive: Receive = ledOff

  private def turnOn() = {
    write(1)
    context become ledOn
  }

  private def turnOff() = {
    write(0)
    context become ledOff
  }

  private def write(i: Int) = gpio.write(i)
}

object Led {
  def props(ledPin: Int) = Props(new Led(ledPin))

  object Protocol {
    case object Clock
    case object Off
  }
}
