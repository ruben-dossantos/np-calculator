package Calculator

import akka.actor.{Props, ActorSystem}

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/21/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
object PingPongTest  extends App{
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong],name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name ="ping")
  ping ! StartMessage

}
