package Calculator

import akka.actor._
import unfiltered.request._
import akka.actor.{Props, ActorSystem}
import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask
import concurrent.Await
import unfiltered.response.ResponseString
import unfiltered.netty.Http


object WebServer extends App {


  var actor: ActorRef = null
  var httpServer: Http = null


  def Start() {
    val system = ActorSystem("WebServer")
    actor = system.actorOf(Props[MultipleCalcActor], "Actor")
    val server = unfiltered.netty.cycle.Planify {
      case req@POST(Path("/number")) => {
        number(Body.string(req).toInt)
        ResponseString("OK")
      }
      case req@POST(Path("/operation")) => {
        val result = operation(Body.string(req))
        ResponseString(result)
      }
      case _ => ResponseString("erro")
    }
    httpServer = unfiltered.netty.Http(8080).plan(server)
    httpServer.start()
  }

  def Stop() {
    httpServer.stop()
  }

  def number(num: Int) {
    println("Numero = " + num)
    actor ! num
  }

  def operation(op: String): String = {
    if (op.length == 1) {
      op.charAt(0) match {
        case '+' => Action('+')
        case '-' => Action('-')
        case '*' => Action('*')
        case '/' => Action('/')
        case _ => "Operador desconhecido!"
      }
    }
    else {
      "Demasiados caracteres na operacao!"
    }
  }

  def Action(op: Char): String = {
    implicit val timeout = Timeout(5 seconds)
    val future = actor ? op
    val result = Await.result(future, timeout.duration).asInstanceOf[Option[Double]]
    println("Result = " + result)
    result match {
      case Some(num) => num.toString
      case None => throw new Exception("Resultado Invalido")
    }
  }
}
