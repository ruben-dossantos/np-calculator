package MQCalculator

import akka.actor.{ActorRef, Props, ActorSystem}
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel

object MQServer{

  var system:ActorSystem = null
  var actor: ActorRef = null
  var connection: Connection = null
  var channel: Channel = null

  def Connect(){
    connection = MQConnection.getConnection()
    channel = connection.createChannel()
  }


  def Start(routingKey:String){
    system = ActorSystem("CalculatorSystem2")
    actor = system.actorOf(Props(new MQServerActor(channel, routingKey)), name="actor")
    actor ! MQServerActor.START


  }

  def Disconnect(){

    channel.close()
    connection.close()
  }
}
