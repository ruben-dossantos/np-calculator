package MQCalculator

import java.util.UUID
import com.rabbitmq.client.AMQP.BasicProperties

class MQClient() extends Similarity{

  val replyQueueName = channel.queueDeclare().getQueue
  channel.basicConsume(replyQueueName, true, consumer)

  def SendNumber(num:Int, routingKey:String) {
    val numString = num.toString
    channel.basicPublish("", Config.RABBITMQ_REQUEST_QUEUE, null, numString.getBytes)

  }

  def SendOperation(op:String, routingKey:String) = {
    var response: String = null
    val corrId = UUID.randomUUID().toString
    var result: Option[Double] = None

    val props = new BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName).build



    channel.basicPublish("", Config.RABBITMQ_REQUEST_QUEUE, props, op.getBytes)

    var continue = true
    while (continue){

      try{
        val delivery = consumer.nextDelivery()
        if (delivery.getProperties.getCorrelationId.equals(corrId)){
          response = new String(delivery.getBody)
          val aux = response.split("\\(")
          aux(0) match {
            case "None" => {
              result = None
              continue = false
            }
            case "Some" =>{
              val aux2 = aux(1).split("\\)")
              result = Some(aux2(0).toDouble)
              continue = false
            }
          }
        }
      }
      catch{
        case e: Exception => "Erro"
      }
    }
    result
  }
}
