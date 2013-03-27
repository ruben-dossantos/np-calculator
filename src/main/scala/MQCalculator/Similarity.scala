package MQCalculator

import com.rabbitmq.client._


trait Similarity {

  val factory = new ConnectionFactory
  factory.setHost(Config.RABBITMQ_HOST)
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  val consumer = new QueueingConsumer(channel)

  def StopMQ(){
    channel.close()
    connection.close()
  }
}
