package cucumber

import api.scala.{ScalaDsl, EN}
import org.junit.Assert._
import Calculator._
import akka.actor.{ActorRef, Props, ActorSystem}
import scala.Some
import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask
import concurrent.Await


class CalculatorTest extends ScalaDsl with EN {

  var result:Option[Double] = None


  Given("""^a number (-?\d+)$"""){ (arg0:Int) =>
    Calc.push(arg0)
  }


  Given("""^an operation ([*+/-])$"""){ (arg0:Char) =>
    result = Calc.op(arg0)
  }

  When("""^these are added$"""){ () =>
    result = Calc.op('+')
  }

  When("""^these are multiplied$"""){ () =>
    result = Calc.op('*')
  }

  When("""^these are subtracted$"""){ () =>
    result = Calc.op('-')
  }

  When("""^these are divided$"""){ () =>
    result = Calc.op('/')
  }

  Then("""^result should be (-?\d+)$"""){ (arg0:Int) =>
    Calc.clean()
    result match{
      case Some(num)=>assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    }

  }
  Then("""^result is (-?\d+)$"""){ (arg0:Int) =>
    Calc.clean()
    result match{
      case Some(num)=>assertEquals(num,arg0,0)
      case None => throw new Exception("Resultado invalido!")
    }

  }


  var map: Map[String,MultipleCalc] = Map()
  var results:Map[String,Option[Double]] = Map()

  Given("""^a calculator named "([^"]*)"$"""){ (arg0:String) =>
    map+=(arg0->new MultipleCalc)
  }

  When("""^a number (-?\d+) is sent to "([^"]*)"$"""){ (arg0:Int, arg1:String) =>
    val arg=arg1
    val mc:MultipleCalc = map(arg)
    mc.push(arg0)
  }

  When("""^an operation "([^"]*)" is sent to "([^"]*)"$"""){ (arg0:Char, arg1:String) =>
    val arg=arg1
    val mc:MultipleCalc = map(arg)
    val result= mc.op(arg0)
    results+=(arg->result)
  }

  Then("""^the result in "([^"]*)" is (\d+)$"""){ (arg0:String, arg1:Int) =>
    val arg=arg0
    val mc:MultipleCalc = map(arg)

    mc.clean()
    results(arg) match{
      case Some(num)=>assertEquals(num,arg1,0)
      case None => throw new Exception("Resultado invalido!")
    }
  }



  val system = ActorSystem("CalculatorSystem")
  var actors : Map[String,ActorRef] = Map()
  var actorsResults : Map[String,Option[Double]] = Map()
  Given("""^a calculator actor named "([^"]*)"$"""){ (arg0:String) =>
    val calc = arg0
    val mcActor = system.actorOf(Props[MultipleCalcActor],name = calc)
    actors += (calc->mcActor)
  }

  When("""^a number (-?\d+) is sent to actor "([^"]*)"$"""){ (arg0:Int, arg1:String) =>
    val calc = arg1
    val mcActor = actors(calc)
    mcActor ! arg0

  }


  When("""^an operation "([^"]*)" is sent to actor "([^"]*)"$"""){ (arg0:Char, arg1:String) =>
    val op = arg0
    val calc = arg1
    val mcActor = actors(calc)
    implicit val timeout = Timeout(5 seconds)
    val future = mcActor ? op
    val result = Await.result(future, timeout.duration).asInstanceOf[Option[Double]]
    actorsResults += (calc->result)
  }

  Then("""^the result in actor "([^"]*)" is (\d+)$"""){ (arg0:String, arg1:Int) =>
    val calc = arg0

    actorsResults(calc) match{
      case Some(num)=>assertEquals(num,arg1,0)
      case None => throw new Exception("Resultado invalido!")
    }

  }


}