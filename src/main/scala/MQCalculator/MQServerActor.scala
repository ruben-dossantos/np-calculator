package MQCalculator

import akka.actor.Actor
import com.rabbitmq.client._
import MQCalculator.MQServerActor.{STOP, START}


object MQServerActor{
  object START
  object STOP
}
class MQServerActor(routingKey:String) extends Actor with Similarity{

  def receive = {
    case START =>{
      startReceiving()
    }
    case STOP => {
      StopMQ()
    }
  }




  def startReceiving() {

    val calculator = new MultipleCalc()
    var result: Option[Double] = None


    channel.queueDeclare(Config.RABBITMQ_REQUEST_QUEUE, false, false, false, null)

    channel.basicConsume(Config.RABBITMQ_REQUEST_QUEUE, true, consumer)





    while(true){
      val delivery = consumer.nextDelivery()

      val props = delivery.getProperties
      val replyProps = new AMQP.BasicProperties.Builder().correlationId(props.getCorrelationId).build

      try{
        val message = new String(delivery.getBody)
        val n = Integer.parseInt(message)

        calculator.push(n)
      }
      catch{
        case e:NumberFormatException => {

          val message = new String(delivery.getBody)

          if(message.length == 1){
            val operator = message.charAt(0)
            operator match {
              case '+' => {
                result = calculator.op(operator)
                response(props, replyProps, result)
              }
              case '-' => {
                result = calculator.op(operator)
                response(props, replyProps, result)
              }
              case '/' => {
                result = calculator.op(operator)
                response(props, replyProps, result)
              }
              case '*' => {
                result = calculator.op(operator)
                response(props, replyProps, result)
              }
              case _ => {
                println("Erro no reconhecimento do operador!")
                response(props, replyProps, result)
              }
            }

          }
          else{
            println("Erro! Mais do que um caracter para a operação")
            response(props, replyProps, result)
          }
        }
        case e: Exception => println("Erro")
      }
    }
  }

  def response(props: BasicProperties, replyProps: AMQP.BasicProperties, result:Option[Double]){
    channel.basicPublish("", props.getReplyTo, replyProps, result.toString.getBytes)
    //channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
  }
}
