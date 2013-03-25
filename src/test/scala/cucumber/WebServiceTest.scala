package cucumber

import api.scala.{ScalaDsl, EN}
import Calculator.{WebServerString}
import org.junit.Assert._
import dispatch._

class WebServiceTest  extends ScalaDsl with EN {

  var result:Option[Double]=None
  var resultExp:String =""

  Given("""^a web service exposing the Calculator$"""){ () =>
    WebServerString.Start()
    Thread.sleep(1000)

  }

  When("""^I POST number (\d+) to /number/$"""){ (arg0:Int) =>
    val urlLink = url("http://127.0.0.1:8000/number")
    urlLink << arg0.toString
    val res = Http(urlLink OK as.String)
    res.apply()
  }

  When("""^I POST an operation "([^"]*)" to /operation/$"""){ (arg0:Char) =>
    val urlLink = url("http://127.0.0.1:8000/operation")
    urlLink << arg0.toString
    val res = Http(urlLink OK as.String)
    res.apply()
    resultExp = res.toString()
  }

  Then("""^the response is (\d+)$"""){ (arg0:Int) =>
    /*result match{
      case Some(num) => assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    } */
    val aux = resultExp.trim.split("=")
    val aux2 = aux(1).split("\\)")
    resultExp = aux2(0).trim
    val arg:Double = resultExp.toDouble
    assertEquals(arg,arg0,0)
    println("Resultado = "+resultExp)
    WebServerString.Stop()
  }

}
