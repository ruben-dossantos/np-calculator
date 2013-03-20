package cucumber

import api.scala.{ScalaDsl, EN}
import org.junit.Assert._
import Calculator.Calc
import Calculator.MultipleCalc
import scala.Some


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



}