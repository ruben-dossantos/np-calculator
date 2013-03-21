package Calculator

import akka.actor.Actor

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/21/13
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
class Pong extends Actor{
  def receive = {
    case PingMessage =>
    println("pong")
    sender ! PongMessage
    case StopMessage =>
    println("pong stopped")
    context.stop(self)
  }

}
