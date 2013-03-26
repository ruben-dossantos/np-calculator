package RabbitMQ4

import com.rabbitmq.client.{QueueingConsumer, ConnectionFactory}

/**
  * Created with IntelliJ IDEA.
  * User: ruben
  * Date: 3/25/13
  * Time: 5:12 PM
  * To change this template use File | Settings | File Templates.
  */
object ReceiveLogsDirect {

   private val EXCHANGE_NAME = "direct_logs"

   def main(args: Array[String]) {
     val factory: ConnectionFactory = new ConnectionFactory
     factory.setHost("localhost")
     val connection = factory.newConnection()
     val channel = connection.createChannel()

     channel.exchangeDeclare(EXCHANGE_NAME, "direct")

     val queueName = channel.queueDeclare().getQueue

     val severities:Array[String] = new Array[String](1)
     severities(0) = "warning"

     if (severities.length<1){
       println("Usage: ReceiveLogsDirect [info] [warning] [error]")
       System.exit(1)
     }

     for (severity<-severities){
       channel.queueBind(queueName, EXCHANGE_NAME, severity)
     }

     println("[*] Waiting for messages. To exit press CTRL+C")

     val consumer: QueueingConsumer = new QueueingConsumer(channel)
     channel.basicConsume(queueName, true, consumer)

     while(true){
       val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()
       val message = new String(delivery.getBody)
       val routingKey = delivery.getEnvelope.getRoutingKey

       println(s"[x] Received '$routingKey' : '$message'")
     }

   }

 }
