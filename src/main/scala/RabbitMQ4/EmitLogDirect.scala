package RabbitMQ4

import com.rabbitmq.client.ConnectionFactory

object EmitLogDirect {

   private val EXCHANGE_NAME = "direct_logs"

   def getSeverity(strings: Array[String]) = {
     if (strings.length < 1)
       "info"
     strings(0)
   }


   def getMessage(strings: Array[String]) = {
     if(strings.length<2)
       "Hello World"
     joinStrings(strings, " ", 1)
   }

   def joinStrings(strings: Array[String], delimiter: String, startIndex:Int) = {
     val length = strings.length
     if (length == 0)
       ""
     if (length<startIndex)
       ""
     val words:StringBuilder = new StringBuilder(strings(startIndex))
     for (i<- startIndex+1 until length){
       words.append(delimiter).append(strings(i))
     }
     words.toString()
   }

   def main(args: Array[String]) {
     val factory: ConnectionFactory = new ConnectionFactory
     factory.setHost("localhost")
     val connection = factory.newConnection()
     val channel = connection.createChannel()

     channel.exchangeDeclare(EXCHANGE_NAME, "direct")

     val messages:Array[String] = new Array[String](5)
     messages(0) = "First"
     messages(1) = "Second"
     messages(2) = "Third"
     messages(3) = "Fourth"
     messages(4) = "Fifth"

     val severities:Array[String] = new Array[String](5)
     severities(0) = "info"
     severities(1) = "error"
     severities(2) = "warning"
     severities(3) = "info"
     severities(4) = "error"


     /*val severity = getSeverity(severities)
     val message = getMessage(messages)

     channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes)
     println(s"[x] Sent '$severity' : '$message'")*/

     for (i<-0 until messages.length){
      channel.basicPublish(EXCHANGE_NAME, severities(i), null, messages(i).getBytes)
      println("[x] Sent '"+severities(i)+"' : '"+messages(i)+"'")
     }


     channel.close()
     connection.close()

   }

 }
