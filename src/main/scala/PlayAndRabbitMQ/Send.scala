package PlayAndRabbitMQ

import Recv._


object Send {

  def startSending = {
    val connection = RabbitMQConnection.getConnection()

    val sendingChannel = connection.createChannel()

    sendingChannel.queueDeclare(Config.RABBITMQ_QUEUE, false, false, false, null)

    val message = "Hello World!"

    sendingChannel.basicPublish("", Config.RABBITMQ_QUEUE, null, message.getBytes)
    println("[x] Sent '" + message + "'")

    sendingChannel.close()
    connection.close()
  }

  def main(args: Array[String]) {
    startSending
    startSending
    startSending
    startReceiving
    startSending
    startSending
    startSending



  }

}