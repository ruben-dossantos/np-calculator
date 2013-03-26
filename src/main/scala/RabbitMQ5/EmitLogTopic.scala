package RabbitMQ5

import com.rabbitmq.client.{Channel, Connection, ConnectionFactory}

object EmitLogTopic {
  private val EXCHANGE_NAME = "topic_logs"

  def getRouting(strings: Array[String]): String = {
    if (strings.length<1)
      "anonymous.info"
    strings(0)
  }

  def joinStrings(strings: Array[String], delimiter: String, startIndex: Int): String = {
    val length = strings.length
    if ( length == 0 )
      ""
    if (length < startIndex)
      ""
    val words:StringBuilder = new StringBuilder(strings(startIndex))
    for (i<-startIndex until length)
      words.append(delimiter).append(strings(i))
    words.toString()
  }

  def getMessage(strings: Array[String]): String ={
    if (strings.length<2)
      "Hello World"
    joinStrings(strings, " ", 1)
  }

  def main(args: Array[String]) {
    var connection:Connection = null
    var channel: Channel = null
    try{
      val factory: ConnectionFactory = new ConnectionFactory
      factory.setHost("localhost")

      connection = factory.newConnection()
      channel = connection.createChannel()

      channel.exchangeDeclare(EXCHANGE_NAME, "topic")

      val strings:Array[String]=new Array[String](2)
      strings(0) = "kern.critical"
      strings(1) = "A critical kernel error"

      val routingKey = getRouting(strings)
      val message = getMessage(strings)

      channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes)
      println("[x] Sent '" + routingKey + "':'" + message + "'")
    }
    catch{
      case e: Exception=>println("Erro!")
    }
    finally {
      if (connection != null){
        try{
          connection.close()
        }
        catch {
          case _ : Exception =>
        }
      }
    }

  }
}
