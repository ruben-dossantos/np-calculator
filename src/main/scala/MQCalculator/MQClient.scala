package MQCalculator

import com.rabbitmq.client.QueueingConsumer
import java.util.UUID
import com.rabbitmq.client.AMQP.BasicProperties

class MQClient() {
  var connection = MQConnection.getConnection()
  var channel = connection.createChannel()

  var result: Option[Double] = None
  private val consumer = new QueueingConsumer(channel)
  private val replyQueueName = channel.queueDeclare().getQueue
  channel.basicConsume(replyQueueName, true, consumer)


  def SendNumber(num:Int, routingKey:String) {
    val numString = num.toString
    channel.basicPublish("", Config.RABBITMQ_REQUEST_QUEUE, null, numString.getBytes)
    println("[x] Sent '" + routingKey + "':'" + numString + "'")
  }

  def SendOperation(op:String, routingKey:String){
    var response: String = null
    val corrId = UUID.randomUUID().toString

    val props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build

    println("[x] Sent '" + routingKey + "':'" + op + "'")

    channel.basicPublish("", Config.RABBITMQ_REQUEST_QUEUE, props, op.getBytes)

    var continue = true
    while (continue){
      val delivery = consumer.nextDelivery()
      if (delivery.getProperties.getCorrelationId.equals(corrId)){
        println("recebi resposta")
        response = new String(delivery.getBody)
        result = Some(response.toDouble)
        continue = false
      }
    }
    channel.close()
    connection.close()
  }

  def getResult = {
    result
  }

}
