package RabbitMQ3

import com.rabbitmq.client.{QueueingConsumer, ConnectionFactory}

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/25/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
object ReceiveLogs {

  private val EXCHANGE_NAME = "logs"

  def main(args: Array[String]) {
    val factory: ConnectionFactory = new ConnectionFactory
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")

    val queueName = channel.queueDeclare().getQueue
    channel.queueBind(queueName, EXCHANGE_NAME, "")

    println("[*] Waiting for messages. To exit press CTRL+C")

    val consumer: QueueingConsumer = new QueueingConsumer(channel)
    channel.basicConsume(queueName, true, consumer)

    while(true){
      val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()
      val message = new String(delivery.getBody)

      println(s"[x] Received '$message'")
    }

  }

}
