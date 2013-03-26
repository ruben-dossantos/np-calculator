package MQCalculator

import akka.actor.Actor
import com.rabbitmq.client.{AMQP, BasicProperties, QueueingConsumer, Channel}
import MQCalculator.MQServerActor.START


object MQServerActor{
  object START
}
class MQServerActor(channel:Channel, routingKey:String) extends Actor{

  def receive = {
    case START =>{
      startReceiving()
    }
  }

  def startReceiving() {
    val calculator = new MultipleCalc()
    var result: Double = 0

    channel.queueDeclare(Config.RABBITMQ_REQUEST_QUEUE, false, false, false, null)
//    channel.basicQos(1);

    val consumer = new QueueingConsumer(channel)

    channel.basicConsume(Config.RABBITMQ_REQUEST_QUEUE, true, consumer)


    println("[*] Waiting for messages. To exit press CTRL+C")
    var elements = 0

    while(elements != 3){
      val delivery: QueueingConsumer.Delivery = consumer.nextDelivery()

      val props: BasicProperties = delivery.getProperties
      val replyProps = new AMQP.BasicProperties.Builder().correlationId(props.getCorrelationId).build

      try{
        val message = new String(delivery.getBody)
        val n = Integer.parseInt(message)
        println("[x] Received number '" + routingKey + "':'" + n + "'")
        calculator.push(n)
        elements += 1
      }
      catch{
        case e:NumberFormatException => {
          val message = new String(delivery.getBody)
          println("[x] Received operation'" + routingKey + "':'" + message + "'")
          result = calculator.op(message.charAt(0))
          elements += 1
        }
        case e: Exception => println("Erro")
      }
      if (elements == 3){
        println("cenas result")
        channel.basicPublish("", props.getReplyTo, replyProps, result.toString.getBytes)
//        channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
      }
    }
    /*try{
      message = new String(delivery.getBody)
      println("[x] Received '"+ routingKey +"':'" + message + "'")
      val num = Integer.parseInt(message)
      calculator.push(num)
      println("Numero pushed")
    }
    catch{
      case e:Exception =>{
        try{
          val op = message.charAt(0)
          result = calculator.op(op)
          println("Operacao pushed")
        }
        catch{
          case e:Exception =>{
            println("Erro!")
          }
        }
        finally {
          channel.basicPublish("", props.getReplyTo, replyProps, result.toString.getBytes)

          channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
        }
      }
    }*/

  }
}
