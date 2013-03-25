package PlayAndRabbitMQ

import com.rabbitmq.client.QueueingConsumer

object Recv {

  def startReceiving {
    val connection = RabbitMQConnection.getConnection()

    val channel = connection.createChannel()

    channel.queueDeclare(Config.RABBITMQ_QUEUE, false, false, false, null)

    println("[*] Waiting for messages. To exit press CTRL+C")

    val consumer = new QueueingConsumer(channel)

    channel.basicConsume(Config.RABBITMQ_QUEUE, true, consumer)

    while(true){
      val delivery = consumer.nextDelivery()
      val message:String = new String(delivery.getBody)
      println("[x] Received '"+message+"'")
    }
  }

}
