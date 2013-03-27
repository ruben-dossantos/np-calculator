package MQCalculator

object Config {
  val RABBITMQ_HOST = "localhost"
  val RABBITMQ_REQUEST_QUEUE = "reply_queue"
  val RABBITMQ_EXCHANGE = "topic_calculator"
}