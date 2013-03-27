package MQCalculator

import akka.actor.{ActorRef, Props, ActorSystem}
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel

object MQServer{

  var system:ActorSystem = null
  var actor: ActorRef = null

  def Start(routingKey:String){
    system = ActorSystem("CalculatorSystem2")
    actor = system.actorOf(Props(new MQServerActor(routingKey)), name="actor")
    actor ! MQServerActor.START
  }

  def Disconnect(){
    actor ! MQServerActor.STOP

  }
}
