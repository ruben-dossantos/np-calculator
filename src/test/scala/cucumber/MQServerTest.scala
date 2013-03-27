package cucumber

import api.scala.{EN, ScalaDsl}
import MQCalculator.{MQClient, MQServer}
import org.junit.Assert._
import scala.Some


class MQServerTest extends ScalaDsl with EN{

  var sender: MQClient = null
  var result: Option[Double]= None

  Given("""^a rabbitmq server connection$"""){ () =>

  }

  Given("""^a service listening to routing key "([^"]*)"$"""){ (arg0:String) =>
    MQServer.Start(arg0)
  }

  Given("""^a client$"""){ () =>
    Thread.sleep(500)
    sender = new MQClient()
  }

  When("""^the client sends number (-?\d+) to the server with the key "([^"]*)"$"""){ (arg0:Int, arg1:String) =>
    sender.SendNumber(arg0, arg1)
  }

  When("""^the client sends the operator "([^"]*)" to the server with the key "([^"]*)"$"""){ (arg0:String, arg1:String) =>
    result = sender.SendOperation(arg0, arg1)

  }

  Then("""^the service should acquire all three elements$"""){ () =>

  }

  Then("""^guarantee their order$"""){ () =>

  }

  Then("""^process the result and send it back to the client$"""){ () =>

  }

  Then("""^the client should receive a reply with the value (-?\d+)$"""){ (arg0:Int) =>
    result match{
      case Some(num)=>{
        assertEquals(num,arg0,0)
      }
      case None => println("Resultado Inválido")
    }
    sender.StopMQ()
    MQServer.Disconnect()

  }

}
