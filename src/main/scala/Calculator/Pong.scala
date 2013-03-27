package Calculator

import akka.actor.Actor


class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println("pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
  }

}
