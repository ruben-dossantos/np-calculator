package RabbitMQ6

import com.rabbitmq.client._

object RPCServer {

  private val RPC_QUEUE_NAME = "rpc_queue"

  def fib(n: Int): Int ={
    n match{
      case 0 => 0
      case 1 => 1
      case n => fib(n-1)+fib(n-2)
    }
  }

  def main(args: Array[String]) {
    var connection: Connection = null
    var channel: Channel = null
    try{
      val factory = new ConnectionFactory
      factory.setHost("localhost")

      connection = factory.newConnection
      channel = connection.createChannel()

      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null)

      channel.basicQos(1)

      val consumer:QueueingConsumer = new QueueingConsumer(channel)

      channel.basicConsume(RPC_QUEUE_NAME, false, consumer)

      println("[x] Awaiting RPC requests")

      while(true){
        var response = ""
        val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()

        val props: BasicProperties = delivery.getProperties
        val replyProps = new AMQP.BasicProperties.Builder().correlationId(props.getCorrelationId).build
        
        try{
          val message = new String(delivery.getBody, "UTF-8")
          val n = Integer.parseInt(message)

          println("[.] fib("+ message + ")")
          response = "" + fib(n)
        }
        catch{
          case e: Exception => println("Erro")
        }
        finally{
          channel.basicPublish("", props.getReplyTo, replyProps, response.getBytes("UTF-8"))

          channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
        }
      }
    }
    catch{
      case e: Exception => println("Erro 2")
    }
    finally {
      if (connection != null){
        try{
          connection.close()
        }
        catch{
          case e: Exception => println("Erro 3")
        }
      }
    }
  }

}
