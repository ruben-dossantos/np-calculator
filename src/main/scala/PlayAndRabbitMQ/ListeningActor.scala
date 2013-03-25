package PlayAndRabbitMQ

import com.rabbitmq.client.{QueueingConsumer, Channel}
import akka.actor.Actor
import PlayAndRabbitMQ.ListeningActor.START


object ListeningActor {
  object START
}


class ListeningActor(channel: Channel, queue: String) extends Actor {

  def receive = {
    case START => startReceiving()
  }

  def startReceiving() {
    val consumer = new QueueingConsumer(channel)
    channel.basicConsume(queue, true, consumer)

    while(true){
      val delivery = consumer.nextDelivery()
      val msg = new String(delivery.getBody)
      val name = self.path.name
      println(s"($name) : $msg")
    }
  }
}
