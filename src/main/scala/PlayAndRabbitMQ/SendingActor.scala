package PlayAndRabbitMQ

import com.rabbitmq.client.{MessageProperties, Channel}
import akka.actor.Actor

class SendingActor(channel: Channel, queue:String) extends Actor{

  def receive = {
    case msg: String => {
      println("[x] Sent '" + msg + "'")
      channel.basicPublish("", queue, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes)
    }
    case _ =>
  }

}