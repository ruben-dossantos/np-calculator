package RabbitMQ5

import com.rabbitmq.client.{QueueingConsumer, ConnectionFactory, Channel, Connection}


object ReceiveLogsTopic {

  private val EXCHANGE_NAME = "topic_logs"

  def main(args: Array[String]) {
    var connection:Connection = null
    var channel: Channel = null
    try{
      val factory: ConnectionFactory = new ConnectionFactory
      factory.setHost("localhost")

      connection = factory.newConnection()
      channel = connection.createChannel()

      channel.exchangeDeclare(EXCHANGE_NAME, "topic")
      val queueName = channel.queueDeclare().getQueue

      val strings:Array[String]=new Array[String](1)
      strings(0) = "error.*"

      if (strings.length < 1){
        println("Usage: ReceiveLogsTopic [binding_key]...")
        System.exit(1)
      }

      for (bindingKey <- strings){
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey)
      }

      println("[*] Waiting for messages. To exit press CTRL+C")

      val consumer: QueueingConsumer = new QueueingConsumer(channel)
      channel.basicConsume(queueName, true, consumer)

      while(true){
        val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()
        val message = new String(delivery.getBody)
        val routingKey = delivery.getEnvelope.getRoutingKey

        println("[x] Received '" + routingKey + "':'" + message + "'")

      }



    }
    catch{
      case e: Exception => println("Erro!")
    }
    finally {
      if (connection != null){
        try{
          connection.close
        }
        catch{
          case e: Exception =>
        }
      }
    }
  }

}
