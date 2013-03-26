package MQCalculator

import com.rabbitmq.client.{ConnectionFactory, Connection}

object MQConnection {

  def getConnection(): Connection = {
        val factory = new ConnectionFactory
        factory.setHost(Config.RABBITMQ_HOST)
        factory.newConnection()
  }


}
