package com.virtuslab.edison.demo

import akka.actor.{ Props, Actor, Cancellable }

import scala.concurrent.duration._

/**
 * Created by rzeznik on 01.05.15.
 */
class Blinky(ledPin: Int) extends Actor {
  import Blinky.Protocol._
  import Led.Protocol._

  import context.dispatcher

  private var blinkSchedule: Option[Cancellable] = None
  private val led = context.actorOf(Led.props(ledPin))

  def notBlinking: Receive = {
    case Signal => startBlinking
  }

  def blinking: Receive = {
    case Signal => stopBlinking
  }

  override def receive: Receive = notBlinking

  private def startBlinking() = {
    blinkSchedule = Some(scheduleBlink)
    context become blinking
  }

  private def stopBlinking() = {
    blinkSchedule foreach { _.cancel() }
    blinkSchedule = None
    led ! Off
    context become notBlinking
  }

  private def scheduleBlink() = context.system.scheduler.schedule(
    initialDelay = 0.millis,
    interval = 400.millis,
    receiver = led,
    message = Clock
  )
}

object Blinky {
  def props(ledPin: Int) = Props(new Blinky(ledPin))

  object Protocol {
    case object Signal
  }
}
