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

/**
 * Created with IntelliJ IDEA.
 * User: ruben
 * Date: 3/22/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
object WebServerString extends App{

    var actor: ActorRef = null
    var httpServer: Http = null



    def Start(){

      val system = ActorSystem("WebServer")
      actor = system.actorOf(Props[MultipleCalcActorString],"Actor")

      val server = unfiltered.netty.cycle.Planify {
        case req @ POST (Path("/number")) =>{
          number(Body.string(req).toInt)
          ResponseString("OK")
        }
        case req @ POST (Path("/operation")) => {
          val result = operation(Body.string(req)(0))
          ResponseString(result)
        }
        case _ => ResponseString("erro")
      }
      httpServer = unfiltered.netty.Http(8000).plan(server)
      httpServer.start()
    }

    def Stop(){
      httpServer.stop()
    }

    def number(num:Int){
      println("Numero = "+num)
      actor ! num
    }

    def operation(op:Char) : String = {
      implicit val timeout = Timeout(5 seconds)
      val future = actor ? op
      val result = Await.result(future, timeout.duration).asInstanceOf[String]
      println("Result = "+result)
      result
    }



}
