package cucumber

import api.scala.{ScalaDsl, EN}
import Calculator.WebServer
import org.junit.Assert._
import dispatch._

class WebServiceTest  extends ScalaDsl with EN {

  var result:Option[Double]=None

  Given("""^a web service exposing the Calculator$"""){ () =>
    WebServer.Start()
    Thread.sleep(1000)

  }

  When("""^I POST number (\d+) to /number/$"""){ (arg0:Int) =>
    val svc = url("http://127.0.0.1:8080/number")
    //val svc = host("http://127.0.0.1:8080/number", 8080)
    svc << arg0.toString
    val res = Http(svc OK as.String)
    println("Status: "+res())
  }

  When("""^I POST an operation "([^"]*)" to /operation/$"""){ (arg0:Char) =>
    val svc = url("http://127.0.0.1:8080/operation")
    svc << arg0.toString
    val res = Http(svc OK as.String)
    println("Status: "+res())
  }

  Then("""^the response is (\d+)$"""){ (arg0:Int) =>
    result match{
      case Some(num) => assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    }
    WebServer.Stop()
  }

}
