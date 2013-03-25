package PlayAndRabbitMQ

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.routing.RoundRobinRouter


object Sender {

  def startSending = {
    val connection = RabbitMQConnection.getConnection()

    val channel = connection.createChannel()

    channel.queueDeclare(Config.RABBITMQ_QUEUE, false, false, false, null)

    val prefetchCount = 1
    channel.basicQos(prefetchCount)

    val message = "Hello World"

    val system = ActorSystem("MessageSystem")
    val sActor = system.actorOf(Props(new SendingActor(channel,Config.RABBITMQ_QUEUE)), name="actor")
    //val lActor = system.actorOf(Props(new ListeningActor(channel,Config.RABBITMQ_QUEUE)), name="listeningactor")

    val router = system.actorOf(Props(new ListeningActor(channel,
      Config.RABBITMQ_QUEUE)).withRouter(RoundRobinRouter(5)))

    1 to 5 foreach{
      i => router ! ListeningActor.START
    }

    //router ! ListeningActor.START
    Thread.sleep(500)

    for (i <- 1 to 1000) {
      sActor ! message+" - "+i

    }


  }

}
