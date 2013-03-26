package RabbitMQ6

import com.rabbitmq.client._
import java.util.UUID


object RPCClient {

  private var connection:Connection = null
  private var channel:Channel = null
  private val requestQueueName = "rpc_queue"
  private var replyQueueName = ""
  private var consumer: QueueingConsumer = null

  def main(args: Array[String]) {
    var fibonacciRpc:RPCClient = null
    var response =""
    try{
      fibonacciRpc = new RPCClient()
      
      println("[x] Requesting fib(30)")
      response = fibonacciRpc.call("10")
      println(s"[.] Got '$response'")
    }
    catch{
      case e: Exception => println("Erro")
    }
    finally{
      if (fibonacciRpc != null){
        try{
          fibonacciRpc.close()
        }
        catch{
          case e:Exception => println("Erro")
        }
      }
    }



  }
  
  class RPCClient(){
    val factory = new ConnectionFactory
    factory.setHost("localhost")
    connection = factory.newConnection()
    channel = connection.createChannel()

    replyQueueName = channel.queueDeclare().getQueue
    consumer = new QueueingConsumer(channel)
    channel.basicConsume(replyQueueName, true, consumer)

    def close(){
      connection.close()
    }

    def call(message: String): String= {
      var response: String = null
      val corrId = UUID.randomUUID().toString

      val props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build

      channel.basicPublish("", requestQueueName, props, message.getBytes)
      var continue = true
      while(continue){
        val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()
        if (delivery.getProperties.getCorrelationId.equals(corrId)){
          response = new String(delivery.getBody, "UTF-8")
          continue = false
        }
      }

      response
    }

  }

}
