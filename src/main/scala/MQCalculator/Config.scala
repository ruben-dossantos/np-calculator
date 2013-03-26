package MQCalculator

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/26/13
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
object Config {
  val RABBITMQ_HOST = "localhost"
  val RABBITMQ_REQUEST_QUEUE = "reply_queue"
  val RABBITMQ_EXCHANGE = "topic_calculator"
}