package RabbitMQ3

import com.rabbitmq.client.ConnectionFactory

object EmitLog {

  private val EXCHANGE_NAME = "logs"


  def getMessage(strings: Array[String]) = {
    if(strings.length<1)
      "Hello World"
    joinStrings(strings, " ")
  }

  def joinStrings(strings: Array[String], delimiter: String) = {
    val length = strings.length
    if (length == 0)
      ""
    val words:StringBuilder = new StringBuilder(strings(0))
    for (i<- 1 until length){
      words.append(delimiter).append(strings(i))
    }
    words.toString()
  }

  def main(args: Array[String]) {
    val factory: ConnectionFactory = new ConnectionFactory
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")

    val messages:Array[String] = new Array[String](5)
    messages(0) = "First"
    messages(1) = "Second"
    messages(2) = "Third"
    messages(3) = "Fourth"
    messages(4) = "Fifth"

    val message = getMessage(messages)

    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes)
    println(s"[x] Sent '$message'")

    channel.close()
    connection.close()

  }

}
